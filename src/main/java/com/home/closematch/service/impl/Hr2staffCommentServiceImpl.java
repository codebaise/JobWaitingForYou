package com.home.closematch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.closematch.entity.Hr2staffComment;
import com.home.closematch.entity.dto.SeekerPositionCommentDTO;
import com.home.closematch.service.Hr2staffCommentService;
import com.home.closematch.mapper.Hr2staffCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class Hr2staffCommentServiceImpl extends ServiceImpl<Hr2staffCommentMapper, Hr2staffComment>
implements Hr2staffCommentService{
    @Autowired
    private Hr2staffCommentMapper hr2staffCommentMapper;

    @Override
    public List<SeekerPositionCommentDTO> getSeekerPositionComments(Long seekerId, Long positionId) {
        return hr2staffCommentMapper.selectSeekerPositionComments(seekerId, positionId);
    }
}




