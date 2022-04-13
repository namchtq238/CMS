package com.cms.service;

import com.cms.config.dto.MailDTO;
import com.cms.controller.request.CommentReq;
import com.cms.controller.response.CommentRes;
import com.cms.controller.service.CommentService;
import com.cms.database.CommentRepo;
import com.cms.database.IdeaRepository;
import com.cms.database.StaffRepo;
import com.cms.entity.Comment;
import com.cms.entity.Idea;
import com.cms.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    MailSenderCustom mailSender;

    @Autowired
    IdeaRepository ideaRepository;

    @Autowired
    StaffRepo staffRepo;

    @Override
    public List<CommentRes> getAllComment(Long ideaId) {
        List<Comment> commentR = commentRepo.getAllByIdeaId(ideaId);
        return commentR.stream().map(comment -> {
            CommentRes res = new CommentRes();
            if(comment == null) return null;
            res.setContent(comment.getContent());
            res.setAnonymous(comment.isAnonymous());
            res.setStaffId(comment.getStaff().getId());
            return res;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentReq postComment(CommentReq commentReq) {
        Comment comment = new Comment();
        Idea idea = ideaRepository.getById(commentReq.getIdeaId());
        idea.setLastComment(Instant.now());
        Staff staffIdea = idea.getStaff();
        Staff staffComment = staffRepo.getById(commentReq.getStaffId());
        comment.setContent(commentReq.getContent());
        comment.setAnonymous(commentReq.isAnonymous());
        comment.setIdea(idea);
        comment.setStaff(null);
        comment.setCreatedDate(Instant.now());
        commentRepo.save(comment);
//        MailDTO mailDTO = new MailDTO();
//        mailDTO.setContent("User " + staffComment.getUser().getUserName() + "commented in your idea");
//        mailDTO.setFrom("noreply@gmail.com");
//        mailDTO.setTo(staffIdea.getUser().getEmail());
//        mailDTO.setSubject("User comment");
//        mailSender.sendMail(mailDTO);
        ideaRepository.save(idea);
        return commentReq;
    }
}
