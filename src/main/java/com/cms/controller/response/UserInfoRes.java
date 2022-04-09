package com.cms.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserInfoRes {
    String username;
    String name;
    String address;
    String email;
}
