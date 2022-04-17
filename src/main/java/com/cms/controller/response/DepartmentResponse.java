package com.cms.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class DepartmentResponse {
    Long id;
    Long qaId;
    String name;
    String qaName;
    Instant startDate;
    Instant closureDateIdea;
    Instant closureDate;
}
