package com.home.closematch.controller.admin;

import com.home.closematch.pojo.Msg;
import com.home.closematch.service.CompanyService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(value = "AdminCompany", tags = "后台管理")
@RestController
public class AdminCompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 根据审核状态获取相应的公司列表
     * @param page
     * @param auditStatus
     * @return
     */
    @GetMapping("/backStageManagement/companies")
    public Msg getCompanyList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "auditStatus", defaultValue = "-1") Integer auditStatus){
        return Msg.success("success").add("items",
                companyService.getCompanyListWithAuditStatus(page, auditStatus));
    }
    /**
    * 公司为审核通过
     * @Param 封装{comment: "test", companyId: "3"}
    */
    @DeleteMapping("/backStageManagement/company")
    public Msg accessCompany(@RequestBody Map<String, Object> map) {
//        System.out.println(map);
        if (map.get("companyId") == null && map.get("comment") == null){
            return Msg.fail(400, "Parameter List so litter");
        }
        companyService.banCompanyByCompanyId(Long.valueOf(map.get("companyId").toString()), map.get("comment").toString());
        return Msg.success();
    }

    /**
     * 公司审核通过
     */
    @PutMapping("/backStageManagement/company/{companyId}")
    public Msg banCompany(@PathVariable("companyId") Long companyId) {
        companyService.accessCompanyByCompanyId(companyId);
        return Msg.success();
    }

    /**
     * 获取 当前公司的审核记录
     * @param companyId
     * @return
     */
    @GetMapping("/backStageManagement/company/{companyId}/reviewHistory")
    public Msg getReviewHistory(@PathVariable("companyId") Long companyId) {
        return Msg.success().add("items", companyService.getCompanyReviewHistory(companyId));
    }
}
