package com.cms.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private boolean active = false;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;
}