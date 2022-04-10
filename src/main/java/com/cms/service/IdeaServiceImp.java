package com.cms.service;

import com.cms.config.PaginationT;
import com.cms.config.dto.UploadFileResDTO;
import com.cms.config.storage.GoogleStorage;
import com.cms.controller.response.ListIdeaRes;
import com.cms.controller.service.IdeaService;
import com.cms.database.DocumentRepo;
import com.cms.database.IdeaRepository;
import com.cms.database.UserRepository;
import com.cms.database.converter.IdeaConverter;
import com.cms.entity.Document;
import com.cms.entity.Idea;
import com.cms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IdeaServiceImp implements IdeaService {
    @Autowired
    IdeaRepository ideaRepository;

    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    GoogleStorage googleStorage;

    @Autowired
    UserRepository userRepo;

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

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public void uploadDocumentToGoogleCloud(File file) throws IOException {
        UploadFileResDTO uploadDto = googleStorage.uploadFileToGoogleCloud(file);
        Document document = new Document();
        document.setUrl(uploadDto.getUrl());
        document.setName(uploadDto.getName());
    }

    @Override
    @Scheduled
    public void uploadDocumentInScheduled(Long id, File file) {
        Optional<User> userOpt = userRepo.findById(id);

    }
}
