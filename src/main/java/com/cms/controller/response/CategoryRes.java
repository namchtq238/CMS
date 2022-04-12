package com.cms.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRes {

    String createdDate;

    String name;

    boolean active;

    Long id;

}
