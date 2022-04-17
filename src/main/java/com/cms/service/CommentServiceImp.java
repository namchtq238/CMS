package com.cms.service;

import com.cms.config.dto.MailDTO;
import com.cms.constants.ERole;
import com.cms.controller.request.CommentReq;
import com.cms.controller.response.CommentPostRes;
import com.cms.controller.response.CommentRes;
import com.cms.controller.service.CommentService;
import com.cms.database.CommentRepo;
import com.cms.database.IdeaRepository;
import com.cms.database.UserRepository;
import com.cms.entity.Comment;
import com.cms.entity.Idea;
import com.cms.entity.User;
import com.cms.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
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
    UserRepository userRepository;

    @Autowired
    Mapper mapper;

    @Override
    public List<CommentRes> getAllComment(Long ideaId) {
        List<Comment> commentR = commentRepo.getAllByIdeaId(ideaId);
        return commentR.stream().map(comment -> mapper.entityCommentToCommentRes(comment)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentPostRes postComment(CommentReq commentReq) {
        Comment comment = new Comment();

        Idea idea = ideaRepository.findById(commentReq.getIdeaId())
                .orElseThrow(() -> new RuntimeException("Cannot found idea with id: " + commentReq.getIdeaId()));
        idea.setLastComment(Instant.now());
        Long staffId = idea.getUserId();
        User userIdea = userRepository.getByIdAndRole(idea.getUserId(), ERole.STAFF.getValue())
                .orElseThrow(() -> new RuntimeException("Cannot found user with ID: " + idea.getUserId()));
        Optional<User> staffComment = userRepository.findById(commentReq.getStaffId());
        comment.setContent(commentReq.getContent());
        comment.setAnonymous(commentReq.isAnonymous());
        comment.setIdeaId(idea.getId());
        comment.setUserId(staffId);
        comment.setCreatedDate(Instant.now());
        commentRepo.save(comment);

        //send email
        MailDTO mailDTO = new MailDTO();
        mailDTO.setContent("User " + staffComment.get().getUserName() + "commented in your idea");
        mailDTO.setFrom("gogitek.wibu.love.anal@gmail.com");
        mailDTO.setTo(userIdea.getEmail());
        mailDTO.setSubject("User comment");
        mailSender.sendMail(mailDTO);
        ideaRepository.save(idea);

        CommentPostRes response = new CommentPostRes();
        response.setContent(comment.getContent());
        response.setIdeaId(comment.getIdeaId());
        response.setStaffName(commentReq.isAnonymous() ? null : staffComment.get().getName());

        return response;
    }

    @Override
    public CommentPostRes getCommentById(Long id) {
        Comment comment = commentRepo.findById(id).orElse(null);
        if (comment == null) {
            return null;
        }


        return this.mapToResponse(comment);
    }

    @Override
    public CommentPostRes mapToResponse(Comment comment) {
        User user = userRepository.findById(comment.getUserId()).orElse(null);
        if (user == null) {
            return null;
        }
        CommentPostRes response = new CommentPostRes();
        response.setContent(comment.getContent());
        response.setIdeaId(comment.getIdeaId());
        response.setStaffName(comment.isAnonymous() ? null : user.getName());
        return response;
    }


}
