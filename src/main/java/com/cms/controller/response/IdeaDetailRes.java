package com.cms.controller.response;

import com.cms.config.PaginationT;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IdeaDetailRes {
    Long ideaId;
    PaginationT<String> detailComment;
    Integer totalLike;
    Integer totalDislike;
    Integer totalComment;
    String description;
    String ideaName;
    String url;
    Integer likeStatus;
}
