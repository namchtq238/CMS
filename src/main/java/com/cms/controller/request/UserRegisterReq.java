package com.cms.controller.request;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterReq {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;
}
