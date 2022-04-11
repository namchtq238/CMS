package com.cms.service;

import com.cms.controller.request.CommentReq;
import com.cms.controller.response.CommentRes;
import com.cms.controller.response.ResponseWrapper;
import com.cms.controller.service.CommentService;
import com.cms.database.CommentRepo;
import com.cms.database.IdeaRepository;
import com.cms.database.StaffRepo;
import com.cms.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public ResponseWrapper getAllComment(Long ideaId) {
        List<CommentRes> commentRes = commentRepo.getAllByIdeaId(ideaId).stream().map(comment -> {
            CommentRes res = new CommentRes();
            if(comment == null) return null;
            res.setContent(comment.getContent());
            res.setAnonymous(comment.isAnonymous());
            res.setStaffId(comment.getStaff().getId());
            return res;
        }).collect(Collectors.toList());
        return new ResponseWrapper(true, commentRes.size(), commentRes);
    }

    @Override
    @Transactional
    public CommentReq postComment(CommentReq commentReq) {
        Comment comment = new Comment();
        comment.setContent(commentReq.getContent());
        comment.setAnonymous(commentReq.isAnonymous());
        comment.setIdea(ideaRepository.getById(commentReq.getIdeaId()));
        comment.setStaff(staffRepo.getById(commentReq.getStaffId()));
        commentRepo.save(comment);
        return commentReq;
    }
}
