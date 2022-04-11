package com.cms.controller.service;

import com.cms.controller.request.CommentReq;
import com.cms.controller.response.CommentRes;
import com.cms.controller.response.CommentResWrapper;

import java.util.List;

public interface CommentService {
    CommentResWrapper getAllComment(Long ideaId);
    CommentReq postComment(CommentReq commentReq);
}
