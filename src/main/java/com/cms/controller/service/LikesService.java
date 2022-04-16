package com.cms.controller.service;

import com.cms.controller.request.ChangeStatusReq;

public interface LikesService {
    void changeStatusLike(ChangeStatusReq changeStatusReq);

    int countLike(Long postId);
}
