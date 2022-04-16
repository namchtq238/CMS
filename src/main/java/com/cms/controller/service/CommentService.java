package com.cms.controller.service;

import com.cms.controller.request.CommentReq;
import com.cms.controller.response.CommentPostRes;
import com.cms.controller.response.CommentRes;

import java.util.List;

public interface CommentService {
    List<CommentRes> getAllComment(Long ideaId);
    CommentPostRes postComment(CommentReq commentReq);
}
