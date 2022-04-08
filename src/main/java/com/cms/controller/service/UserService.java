package com.cms.controller.service;

import com.cms.controller.request.UserInfoReq;
import com.cms.controller.request.UserReq;
import com.cms.controller.response.UserInfoRes;
import org.springframework.stereotype.Service;

public interface UserService {
    void registerUser(UserReq req);

    UserInfoRes updateUserInfo(Long id, UserInfoReq req);
}
