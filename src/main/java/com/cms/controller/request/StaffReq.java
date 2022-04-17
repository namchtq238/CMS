package com.cms.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StaffReq {
    String username;
    String name;
    String password;
    String email;
    String position;
    Integer role;
}
