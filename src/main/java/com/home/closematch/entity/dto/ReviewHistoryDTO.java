package com.home.closematch.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 目前只用在公司的历史评判信息get
 * 后期应该用在seeker 和 hr 的账号评判内容上
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewHistoryDTO {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date date;
    private String comment;
}
