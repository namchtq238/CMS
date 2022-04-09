package com.cms.service;

import com.cms.config.PaginationT;
import com.cms.controller.response.ListIdeaRes;
import com.cms.controller.service.IdeaService;
import com.cms.database.IdeaRepository;
import com.cms.database.converter.IdeaConverter;
import com.cms.entity.Idea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class IdeaServiceImp implements IdeaService {
    @Autowired
    IdeaRepository ideaRepository;

    @Override
    public PaginationT<ListIdeaRes> findIdea(Integer page, Integer size) {
        PaginationT<ListIdeaRes> list = new PaginationT<>();
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<IdeaConverter> data = ideaRepository.findAllIdea(pageable);
        list.setItems(data.stream().map(converter -> {
            ListIdeaRes res = new ListIdeaRes();
            if (converter == null) return null;

            res.setCategoryId(converter.getCategory());
            res.setDescription(converter.getDescription());
            res.setCommentList(converter.getDetailComment());
            res.setLikesList(converter.getDetailLikes());
            res.setTimeUp(converter.getTimeUp());
            res.setTotalLike(converter.getTotalLike());
            res.setTotalComment(converter.getTotalComment());
            res.setStaffId(converter.getStaffId());

            return res;
        }).collect(Collectors.toList()));
        list.setTotal(data.getTotalElements());
        return list;
    }
}
