package com.home.closematch.mapper;

import com.home.closematch.entity.dto.SeekerPositionCommentDTO;
import com.home.closematch.entity.Hr2staffComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.home.closematch.entity.Hr2staffComment
 */
@Repository
public interface Hr2staffCommentMapper extends BaseMapper<Hr2staffComment> {
    List<SeekerPositionCommentDTO> selectSeekerPositionComments(@Param("seekerId") Long seekerId, @Param("positionId")Long positionId);
}




