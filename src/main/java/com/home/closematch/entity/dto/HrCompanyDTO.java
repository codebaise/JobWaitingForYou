package com.home.closematch.entity.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.home.closematch.entity.Account;
import com.home.closematch.entity.Company;
import com.home.closematch.entity.Humanresoucres;
import lombok.Data;

/**
 * admin :
 * 初始版本中
 * 获取hr的列表时
 *  一次性获取:
 *      hr信息
 *      hr的company信息
 */
@Data
public class HrCompanyDTO {
    // account
    @JsonSerialize(using = ToStringSerializer.class)
    private Long accountId;
    private String username;
    private Integer isDelete;
    // humanresources
    @JsonSerialize(using = ToStringSerializer.class)
    private Long hrId;
    private String hrName;
    private String curPosition;
    private Integer status; //当前的状态, 如果是0 说明发布的职位什么都不会被显示, 1则说明审核通过
    private Long hrCompanyId; // 默认 0
    // company
    private String companyName;
    private String simpleName;
    private String domain;
    private String description;
    private String scale;
    private Integer auditStatus;

    public void setAccount(Account account){
        this.username = account.getUsername();
        this.isDelete = account.getIsDelete();
    }

    public void setHr(Humanresoucres humanresoucres){
        this.hrId = humanresoucres.getId();
        this.hrName = humanresoucres.getName();
        this.curPosition = humanresoucres.getCurPosition();
        this.status = humanresoucres.getStatus();
        this.hrCompanyId = humanresoucres.getCompanyId();
    }

    public void setCompany(Company company){
        this.companyName = company.getName();
        this.simpleName = company.getSimpleName();
        this.domain = company.getDomain();
        this.description = company.getDescription();
        this.scale = company.getScale();
        this.auditStatus = company.getAuditStatus();
    }

}
