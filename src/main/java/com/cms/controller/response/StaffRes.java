package com.cms.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StaffRes {
    Long staffId;
    String position;
    String name;
    Integer role;
    String email;
    String username;
}
