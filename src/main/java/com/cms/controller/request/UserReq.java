package com.cms.controller.request;

import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class UserReq {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

}
