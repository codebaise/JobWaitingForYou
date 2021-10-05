package com.home.closematch.entity.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * hr对seeker申请做出评判
 */
@Data
public class CheckDeliverVo {
    @NotNull
    private Long deliverId;
    @Min(value = 1, message = "Error Operation")
    @Max(value = 2, message = "Error Operation")
    private Integer checkValue;
}
