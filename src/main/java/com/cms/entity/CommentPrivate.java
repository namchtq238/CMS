package com.cms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CommentPrivate {
    @Id
    @GeneratedValue
    private Long id;

    private Long comment_id;

    private Long staff_id;
}
