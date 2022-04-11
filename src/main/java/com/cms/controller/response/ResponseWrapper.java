package com.cms.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResponseWrapper {
    boolean success;
    Integer count;
    List<?> items;
}
