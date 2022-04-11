package com.cms.service;

import com.cms.config.PaginationT;
import com.cms.config.dto.UploadFileResDTO;
import com.cms.config.storage.GoogleStorage;
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
import com.cms.entity.User;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

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
    private PlatformTransactionManager transactionManager;

    @Autowired
    GoogleStorageInterface googleStorage;

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

    @Override
    public boolean checkClosureTime(String startDate, String endDate) {
        Instant time = Instant.now();
        if(time.compareTo(Instant.parse(startDate)) >= 0 && time.compareTo(Instant.parse(endDate)) <=0 ) return true;
        return false;
    }

    @Override
    public UploadFileResDTO uploadDocumentInScheduled(UploadReq req){
        // Begin transaction here
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus transaction = transactionManager.getTransaction(definition);
        UploadFileResDTO dto = new UploadFileResDTO();
        try{
            Optional<User> userOpt = userRepo.findById(req.getId());
            if(userOpt.isEmpty())
                throw new RuntimeException("Not Found");
            User user = userOpt.get();
            if(!user.getRole().equals(ERole.STAFF.getValue()))
                throw new RuntimeException(String.format("Author is not %s", ERole.STAFF));
            if(!checkClosureTime(req.getStartDate(), req.getEndDate()))
                throw new RuntimeException(String.format("Out of time to submit: %s", new RuntimeException().getLocalizedMessage()));
            File uploadFile = googleStorage.upLoadFile(req.getFile().getName(),req.getFile().getInputStream().toString(),req.getFile().getOriginalFilename().split("\\.")[1]);
            Document document = new Document();
            dto.setUrl(uploadFile.getWebViewLink());
            dto.setName(uploadFile.getName());
            document.setUrl(uploadFile.getWebViewLink());
            document.setName(uploadFile.getName());
            document.setCategoryId(req.getCategoryId());
            document.setUser_id(req.getId());
            documentRepo.save(document);
            //commit
            transactionManager.commit(transaction);
        }catch (Exception ex){
            transactionManager.rollback(transaction);
        }
        return dto;
    }

}
