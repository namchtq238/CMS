package com.cms.service;

import com.cms.config.PaginationT;
import com.cms.config.dto.UploadFileResDTO;
import com.cms.config.storage.FileStorageService;
import com.cms.config.storage.GoogleStorageInterface;
import com.cms.constants.ERole;
import com.cms.controller.request.UploadReq;
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
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IdeaServiceImp implements IdeaService {
    @Autowired
    IdeaRepository ideaRepository;

    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    GoogleStorageInterface googleStorage;

    @Autowired
    FileStorageService fileStorageService;

    @Override
    public PaginationT<ListIdeaRes> findIdea(Long depaId, Integer page, Integer size) {
        PaginationT<ListIdeaRes> list = new PaginationT<>();
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<IdeaConverter> data = ideaRepository.findByCategoryId(depaId, pageable);
        list.setItems(data.stream().map(converter -> {
            ListIdeaRes res = new ListIdeaRes();
            if (converter == null) return null;

            res.setDepartmentId(converter.getCategory());
            res.setDescription(converter.getDescription());
            res.setCommentList(converter.getDetailComment());
            res.setLikesList(converter.getDetailLikes());
            res.setTimeUp(converter.getTimeUp());
            res.setTotalLike(converter.getTotalLike());
            res.setTotalComment(converter.getTotalComment());
            res.setStaffId(converter.getStaffId());
            res.setIdeaId(converter.getId());

            return res;
        }).collect(Collectors.toList()));
        list.setTotal(data.getTotalElements());
        return list;
    }

    public boolean checkClosureTime(String startDate, String endDate) {
        Instant time = Instant.now();
        if(time.compareTo(Instant.parse(startDate)) >= 0 && time.compareTo(Instant.parse(endDate))<= 0) return true;
        return false;
    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public UploadFileResDTO uploadDocumentInScheduled(UploadReq req){
        // Begin transaction here
        Optional<User> userOpt = userRepo.findById(req.getId());
        if(userOpt.isEmpty())
            throw new RuntimeException("Not Found");
        User user = userOpt.get();
        if(!user.getRole().equals(ERole.STAFF.getValue()))
            throw new RuntimeException(String.format("Author is not %s", ERole.STAFF));
        if(!checkClosureTime(req.getStartDate(), req.getEndDate()))
            throw new RuntimeException(String.format("Out of time to submit: %s", new RuntimeException().getLocalizedMessage()));
        String fileName = fileStorageService.storeFile(req.getFile());
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download-file/")
                .path(fileName)
                .toUriString();
        String fileOriginalUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/")
                .path(fileName)
                .toUriString();
        Document document = new Document();
        document.setUrl(fileOriginalUri);
        document.setName(fileName);
        document.setCreatedDate(Instant.now());
        document.setUrlDownload(fileDownloadUri);
        document.setCategoryId(req.getCategoryId());
        document.setUser_id(req.getId());
        documentRepo.save(document);
        //save Idea
//        Idea idea = new Idea();
//        idea.setDocumentId(document.getId());
//        idea.getStaff().setId(req.getId());
//        idea.setStartDate(Instant.parse(req.getStartDate()));
//        idea.setTimeUp(Instant.parse(req.getEndDate()));
//        idea.setCreatedDate(Instant.now());
//        idea.getCategory().setId(req.getCategoryId());
//        idea.setDescription(req.getDescription());
//        ideaRepository.save(idea);

        UploadFileResDTO dto = new UploadFileResDTO(fileName, fileDownloadUri, fileOriginalUri, req.getFile().getContentType(), req.getFile().getSize());
        return dto;
    }
}
