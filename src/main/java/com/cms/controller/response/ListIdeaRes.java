package com.cms.controller.response;

import com.cms.entity.Category;
import com.cms.entity.Comment;
import com.cms.entity.Likes;
import com.cms.entity.Staff;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class ListIdeaRes {
    String timeUp;

    String description;

    Long staffId;

    Long categoryId;

    String likesList;

    String commentList;

    Integer totalLike;

    Integer totalComment;
}