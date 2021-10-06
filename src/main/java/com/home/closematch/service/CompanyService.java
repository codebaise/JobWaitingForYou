package com.home.closematch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.home.closematch.entity.Company;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.closematch.entity.dto.ReviewHistoryDTO;
import com.home.closematch.entity.vo.CompanyDetailInfoVo;
import com.home.closematch.entity.vo.CompanyHrVo;
import com.home.closematch.pojo.PageCompanys;
import com.home.closematch.pojo.PagePositionHr;

import java.util.List;

/**
 *
 */
public interface CompanyService extends IService<Company> {
    /**
     * add / update Company
     */
    void saveOrUpdateCompany(Long hrId, Company company);

    /**
     * admin
     * 获取公司列表和相应的审核状态
     * @return
     */
    IPage<Company> getCompanyListWithAuditStatus(Integer page, Integer auditStatus);

    /**
     * admin
     * 根据companyId ban 公司, 并附加上一条 review的reason
     */
    void banCompanyByCompanyId(Long companyId, String comment);

    /**
     * admin
     * 通过公司信息审核, 审核通过
     */
    void accessCompanyByCompanyId(Long companyId);

    /**
     * 获取公司相应的审核记录, 评论信息
     */
    List<ReviewHistoryDTO> getCompanyReviewHistory(Long companyId);

    /**
     * 通过公司id进行查询公司的相应信息
     * 公司基本信息 , hr的数量, 发布的职位数量
     */
    CompanyDetailInfoVo getCompanyDetailInfoByCompanyId(Long companyId);

    /**
     * 获取公司的几个hr信息, 默认只拉取三个即可
     * 前台展示的信息
     */
    List<CompanyHrVo> getCompanyHrs(Long companyId);

    /**
     * 获取公司的在招职位列表, 因为在基础页面也需要显示几个, 则用limit 限制
     */
    PagePositionHr getCompanyPositions(Long companyId, boolean limit, int page);

    /**
     * 获取公司列表
     * @param page
     * @return
     */
    PageCompanys getCompanyList(int page);

    /**
     * 通过positionId获取公司详情信息
     * 和上面的方法功能一样, 其中调用了该方法
     * 只不过多了一次方法方法
     * @param positionId
     * @return
     */
    CompanyDetailInfoVo getCompanyDetailInfoByPositionId(Long positionId);
}