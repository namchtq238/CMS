package com.cms.controller.service;

import com.cms.controller.request.CommentReq;
import com.cms.controller.response.ResponseWrapper;

public interface CommentService {
    ResponseWrapper getAllComment(Long ideaId);
    CommentReq postComment(CommentReq commentReq);
}
