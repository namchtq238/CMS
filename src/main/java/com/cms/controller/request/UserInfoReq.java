package com.cms.controller.request;

import lombok.Getter;

@Getter
public class UserInfoReq {
    Long id;
    String name;
    String position;
    String email;
    Integer role;
}
