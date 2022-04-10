package com.cms.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentReq {
    String content;
    boolean anonymous;
    Long staffId;
    Long ideaId;
}
