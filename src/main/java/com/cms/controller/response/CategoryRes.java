package com.cms.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryRes {
    String createdDate;

    String description;

    String completedDate;

    boolean active;

    Long qa;

    List<Long> idea;
}
