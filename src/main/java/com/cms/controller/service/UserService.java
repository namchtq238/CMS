package com.cms.controller.service;

import com.cms.controller.request.ChangePasswordReq;
import com.cms.controller.request.UserInfoReq;
import com.cms.controller.request.UserRegisterReq;
import com.cms.controller.response.UserInfoRes;
import com.cms.entity.User;

public interface UserService {
    void registerUser(UserRegisterReq req);

    UserInfoRes updateUserInfo(UserInfoReq req);

    void updatePassword(ChangePasswordReq req);

    User getCurrentUser();
}
