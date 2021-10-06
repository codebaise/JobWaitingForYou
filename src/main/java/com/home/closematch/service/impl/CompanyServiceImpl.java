package com.home.closematch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.closematch.entity.CmReviewAdvise;
import com.home.closematch.entity.Company;
import com.home.closematch.entity.Humanresoucres;
import com.home.closematch.entity.Position;
import com.home.closematch.entity.dto.ReviewHistoryDTO;
import com.home.closematch.entity.vo.CompanyDetailInfoVo;
import com.home.closematch.entity.vo.CompanyHrVo;
import com.home.closematch.entity.vo.CompanysVo;
import com.home.closematch.entity.vo.PositionHrVo;
import com.home.closematch.exception.ServiceErrorException;
import com.home.closematch.pojo.PageCompanys;
import com.home.closematch.pojo.PagePositionHr;
import com.home.closematch.service.HumanresoucresService;
import com.home.closematch.service.PositionService;
import com.home.closematch.service.ReviewAdviseService;
import com.home.closematch.service.CompanyService;
import com.home.closematch.mapper.CompanyMapper;
import com.home.closematch.utils.CompanyUtils;
import com.home.closematch.utils.ReviewUtils;
import com.home.closematch.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company>
implements CompanyService{

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private ReviewAdviseService reviewAdviseService;
    @Autowired
    private HumanresoucresService humanresoucresService;
    @Autowired
    private PositionService positionService;


    /**
     * add / update Company
     * 只要更新了公司信息, 则将公司audit和hr status 置为0
     * @param hrId
     * @param company
     */
    @Override
    public void saveOrUpdateCompany(Long hrId, Company company) {
        Humanresoucres hr = humanresoucresService.getById(hrId);
        company.setAuditStatus(0);
        if (hr.getCompanyId() == null || hr.getCompanyId() == 0){
            companyMapper.insert(company);
            hr.setCompanyId(company.getId());
        } else {
            company.setId(hr.getCompanyId());
            companyMapper.updateById(company);
        }

        hr.setStatus(0);
        humanresoucresService.updateById(hr);

    }

    @Override
    public IPage<Company> getCompanyListWithAuditStatus(Integer page, Integer auditStatus) {
        QueryWrapper<Company> auditQW = new QueryWrapper<Company>();
        if (auditStatus == -1){
            auditQW.gt("audit_status", 0);
        } else {
            auditQW.eq("audit_status", auditStatus);
        }
        return companyMapper.selectPage(new Page<>(page, CommonUtils.perPageSize), auditQW);
    }

    /**
     * 根据companyId ban 公司, 并附加上一条 review的reason
     * 埋坑: 到时候的ID从用户身份中拿
     * @param companyId
     * @param comment
     */
    @Override
    public void banCompanyByCompanyId(Long companyId, String comment) {
        changeCompanyAuditStatus(companyId, CompanyUtils.COMPANY_AUDIT_TYPE_BANNED);
        // 增加 一条 comment
        CmReviewAdvise cmReviewAdvise = new CmReviewAdvise();
        cmReviewAdvise.setReviewType(ReviewUtils.REVIEW_TYPE_COMPANY);
        cmReviewAdvise.setReviewObjId(companyId);
        cmReviewAdvise.setReviewAdminId(1L); // wait
        cmReviewAdvise.setReviewAdvise(comment);

        reviewAdviseService.save(cmReviewAdvise);
    }

    /**
     * 审核通过
     * @param companyId
     */
    @Override
    public void accessCompanyByCompanyId(Long companyId) {
        changeCompanyAuditStatus(companyId, CompanyUtils.COMPANY_AUDIT_TYPE_ACCESS);
    }

    /**
     * 获取公司相应的审核记录, 评论信息
     */
    @Override
    public List<ReviewHistoryDTO> getCompanyReviewHistory(Long companyId) {
        List<CmReviewAdvise> cmReviewAdvises = reviewAdviseService.list(new QueryWrapper<CmReviewAdvise>()
                .eq("review_type", ReviewUtils.REVIEW_TYPE_COMPANY)
                .eq("review_obj_id", companyId));
//        List<CmReviewAdvise> cmReviewAdvises = cmReviewAdviseMapper.selectList(new QueryWrapper<CmReviewAdvise>()
//                .eq("review_type", EntityType.REVIEW_TYPE_COMPANY)
//                .eq("review_obj_id", companyId));
        return cmReviewAdvises.stream()
                .map(item -> new ReviewHistoryDTO(item.getCreateTime(), item.getReviewAdvise()))
                .collect(Collectors.toList());
    }

    /**
     * 通过公司id进行查询公司的相应信息
     * 公司基本信息 , hr的数量, 发布的职位数量
     * @param companyId
     * @return
     */
    @Override
    public CompanyDetailInfoVo getCompanyDetailInfoByCompanyId(Long companyId) {
        Company company = companyMapper.selectById(companyId);
        if (company == null || company.getAuditStatus() == 0)
            throw new ServiceErrorException(400, "该公司信息尚未补全, 正在修改");
        int hrCount = humanresoucresService.count(new QueryWrapper<Humanresoucres>().eq("company_id", companyId));
        List<Humanresoucres> hrList = humanresoucresService.list(new QueryWrapper<Humanresoucres>().eq("company_id", company.getId()));
        if (hrList.size() <= 0)
            throw new ServiceErrorException(400, "该公司信息尚未补全, 正在修改");
        // 使用in拿到发布的职位数量
        List<Long> hrIdList = new ArrayList<>();
        hrList.forEach(hr -> hrIdList.add(hr.getId()));
        int positionCount = positionService.count(new QueryWrapper<Position>().in("hr_id", hrIdList));

        CompanyDetailInfoVo companyDetailInfoVo = new CompanyDetailInfoVo();
        companyDetailInfoVo.setCompanyName(company.getName());
        companyDetailInfoVo.setSimpleName(company.getSimpleName());
        companyDetailInfoVo.setDomain(company.getDomain());
        companyDetailInfoVo.setScale(company.getScale());
        companyDetailInfoVo.setDescription(company.getDescription());
        companyDetailInfoVo.setHrCount(hrCount);
        companyDetailInfoVo.setPositionCount(positionCount);
        ;
        return companyDetailInfoVo;
    }

    /**
     * 前台展示的信息
     * 获取公司的几个hr信息, 默认只拉取三个即可
     * @param companyId
     * @return
     */
    @Override
    public List<CompanyHrVo> getCompanyHrs(Long companyId) {
        List<Humanresoucres> hrList =
                humanresoucresService.list(new QueryWrapper<Humanresoucres>().eq("company_id", companyId).last("limit 3"));
        if (hrList.size() <=0 )
            throw new ServiceErrorException(400, "公司资料不全, 正在修补");
        List<CompanyHrVo> companyHrVos = new ArrayList<>();
        hrList.forEach(hr -> {
            CompanyHrVo companyHrVo = new CompanyHrVo();
            companyHrVo.setHrName(hr.getName());
            companyHrVo.setCurPosition(hr.getCurPosition());
            int positionCount = positionService.count(new QueryWrapper<Position>().eq("hr_id", hr.getId()));
            companyHrVo.setPositionCount(positionCount);
            companyHrVos.add(companyHrVo);
        });
        return companyHrVos;
    }

    /**
     * 获取公司的在招职位列表
     * 因为在公司详情的基础页面也需要显示几个, 则用limit 限制
     */
    @Override
    public PagePositionHr getCompanyPositions(Long companyId, boolean limit, int page) {
        List<Humanresoucres> hrList = humanresoucresService.list(new QueryWrapper<Humanresoucres>().eq("company_id", companyId));
        if (hrList.size() <= 0)
            throw new ServiceErrorException(400, "该公司信息尚未补全, 正在修改");
        // 使用in拿到发布的职位数量
        HashMap<Long, Map<String, String>> hrInfoMap = new HashMap<>();
        // 将hr的信息存在map中, 方便后来获取
        hrList.forEach(hr -> {
            HashMap<String, String> hrInfo = new HashMap<>();
            hrInfo.put("hrName", hr.getName());
            hrInfo.put("hrCurPosition", hr.getCurPosition());
            hrInfoMap.put(hr.getId(), hrInfo);
        });
        QueryWrapper<Position> queryWrapper = new QueryWrapper<Position>().in("hr_id", hrInfoMap.keySet());
        PagePositionHr pagePositionHr = null;
        // 判断是否为获取少量的信息
        if (limit) {
            pagePositionHr = positionService.page(new PagePositionHr(1, 3), queryWrapper);
        } else {
            pagePositionHr = positionService.page(new PagePositionHr(page, CommonUtils.perPageSize), queryWrapper);
        }
        // 用来存储最终的结果
        ArrayList<PositionHrVo> PositionHrVos = new ArrayList<>();
        pagePositionHr.getRecords().forEach(position -> {
            PositionHrVo PositionHrVo = new PositionHrVo();
            Map<String, String> getHrInfo = hrInfoMap.get(position.getHrId());
            PositionHrVo.setHrName(getHrInfo.get("hrName"));
            PositionHrVo.setHrPosition(getHrInfo.get("hrCurPosition"));

            PositionHrVo.setPositionId(position.getId());
            PositionHrVo.setPositionName(position.getPositionName());
            PositionHrVo.setWorkCity(position.getWorkCity());
            PositionHrVo.setMinSalary(position.getMinSalary());
            PositionHrVo.setMinEducation(position.getMinEducation());
            PositionHrVo.setMinExperience(position.getMinExperience());
            PositionHrVos.add(PositionHrVo);
        });
        // 清空其他数据, 并重新设置结果集
        pagePositionHr.setRecords(null);
        pagePositionHr.setPositionHrVos(PositionHrVos);
        return pagePositionHr;
    }

    /**
     * 获取公所列表
     */
    @Override
    public PageCompanys getCompanyList(int page) {
        // 获取所有的公司列表
        PageCompanys companyPage = companyMapper.selectPage(new PageCompanys(page, CommonUtils.perPageSize), new QueryWrapper<Company>().eq("audit_status", CompanyUtils.COMPANY_AUDIT_TYPE_ACCESS));
        List<Company> companyList = companyPage.getRecords();
        // 通过每个公司查hr
        List<CompanysVo> companyVos = new ArrayList<>();
        for (Company company: companyList){
            List<Humanresoucres> hrs = humanresoucresService.list(new QueryWrapper<Humanresoucres>().eq("company_id", company.getId()));
            if (hrs.size() == 0)
                continue;
            ArrayList<Long> hrIds = new ArrayList<>();
            hrs.forEach(hr -> {
                hrIds.add(hr.getId());
            });
            List<Position> positions = positionService.list(new QueryWrapper<Position>().in("hr_id", hrIds));
            if (positions.size() == 0)
                continue;

            CompanysVo companysVo = new CompanysVo();
            companysVo.setCompanyId(company.getId());
            companysVo.setCompanyName(company.getName());
            companysVo.setDomain(company.getDomain());
            companysVo.setScale(company.getScale());
            companysVo.setPositionId(positions.get(0).getId());
            companysVo.setPositionName(positions.get(0).getPositionName());
            companysVo.setMinSalary(positions.get(0).getMinSalary());
            companyVos.add(companysVo);
        }

        companyPage.setCompanysVos(companyVos);
        companyPage.setRecords(null);
        return companyPage;
    }
    /**
     * 通过positionId获取公司详情信息
     * 和上面的方法功能一样, 其中调用了该方法
     * 只不过多了一次方法方法
     */
    @Override
    public CompanyDetailInfoVo getCompanyDetailInfoByPositionId(Long positionId) {
        Position position = positionService.getById(positionId);
        Humanresoucres hr = humanresoucresService.getById(position.getHrId());
        Company company = companyMapper.selectById(hr.getCompanyId());
        CompanyDetailInfoVo companyDetail = getCompanyDetailInfoByCompanyId(company.getId());
        companyDetail.setCompanyId(company.getId());
        return companyDetail;
    }

    /**
     * 私有方法, 改变公司的审核状态
     * @param companyId 欲设置公司id
     * @param setAuditStatus 欲设置的状态
     */
    private void changeCompanyAuditStatus(Long companyId, Integer setAuditStatus) {
        Company company = companyMapper.selectById(companyId);
        if (company == null)
            throw new ServiceErrorException(404, "Not fount the company");
        company.setAuditStatus(setAuditStatus);
        companyMapper.updateById(company);
    }
}




