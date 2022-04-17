package com.cms.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "idea")
public class Idea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "time_up")
    private Instant timeUp;

    @Column(name = "description")
    private String description;

    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "name")
    private String name;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "anonymous")
    private boolean anonymous = false;

    @Column(name = "last_comment")
    private Instant lastComment;

    @Column(name = "user_id")
    private Long userId;

}