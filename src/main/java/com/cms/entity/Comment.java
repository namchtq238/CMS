package com.cms.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "idea_id")
    private Long ideaId;

    @Column(name = "anonymous")
    private boolean anonymous = false;

    @Column(name = "user_id")
    private Long userId;
}