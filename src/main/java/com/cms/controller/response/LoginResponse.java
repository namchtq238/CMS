package com.cms.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    private String token;
    String username;
    String email;
    List<String> roles;
    public LoginResponse(String token,String username, String email, List<String> roles){
        this.token = token;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
