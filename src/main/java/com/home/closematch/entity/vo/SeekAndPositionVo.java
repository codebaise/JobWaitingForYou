package com.home.closematch.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SeekAndPositionVo {
    /**
     * seeker :
     *  id
     *  name
     *  userIdentity
     *  currentStatus
     * position:
     *  positionName
     *  workCity
     * seekerDeliver
     *  status : 审批状态
     */
    // seeker
    @JsonSerialize(using = ToStringSerializer.class)
    private String seekerName;
    private Integer userIdentity;
    private Integer currentStatus;
    // position
    @JsonSerialize(using = ToStringSerializer.class)
    private String positionName;
    private String workCity;
    // seekerDeliver
    private Long deliverId;
    private Integer status;

}
