package com.cms.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRes {
    String content;
    boolean anonymous;
    Long staffId;
}
