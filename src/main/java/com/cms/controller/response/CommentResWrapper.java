package com.cms.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentResWrapper {
    boolean success;
    Integer count;
    List<CommentRes> items;
}
