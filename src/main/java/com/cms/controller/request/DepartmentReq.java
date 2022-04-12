package com.cms.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentReq {
     String name;

     String startDate;

     String closureDateIdea;

     String closureDate;

}
