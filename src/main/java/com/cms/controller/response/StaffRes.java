package com.cms.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffRes {
    Long staffId;
    String position;
    Long userId;
    String name;
    String address;
    Integer role;
    String email;
}
