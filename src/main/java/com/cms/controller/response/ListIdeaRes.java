package com.cms.controller.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ListIdeaRes {
    Long ideaId;

    String timeUp;

    String description;

    Long staffId;

    Long departmentId;

    Integer totalLike;

    Integer totalComment;

    Long categoryId;

    String name;

    String url;

    Integer likeStatus;
}
