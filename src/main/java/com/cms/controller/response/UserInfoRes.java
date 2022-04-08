package com.cms.controller.response;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class UserInfoRes {
    String username;
    String name;
    String address;
    String email;
}
