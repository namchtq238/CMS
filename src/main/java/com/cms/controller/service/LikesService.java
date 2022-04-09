package com.cms.controller.service;

import com.cms.controller.request.ChangeStatusReq;
import com.cms.entity.Staff;

public interface LikesService {
    Integer changeStatusLike(ChangeStatusReq changeStatusReq);

    int countLike(Long postId);
}
