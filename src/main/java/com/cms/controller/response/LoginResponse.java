package com.cms.controller.response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    private String token;
    String username;
    String email;
    String roles;
    public LoginResponse(String token,String username, String email, String roles){
        this.token = token;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
