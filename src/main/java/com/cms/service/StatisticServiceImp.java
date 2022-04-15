package com.cms.service;

import com.cms.controller.response.StatisticResponse;
import com.cms.controller.service.StatisticService;
import com.cms.database.CommentRepo;
import com.cms.database.IdeaRepository;
import com.cms.database.LikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticServiceImp implements StatisticService {
    @Autowired
    LikeRepo likeRepo;

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    IdeaRepository ideaRepository;

    @Override
    public StatisticResponse getListStatistic() {
        
        return null;
    }
}
