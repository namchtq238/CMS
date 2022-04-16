package com.cms.controller.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPostRes {
    String content;
    String staffName;
    Long ideaId;
}
