package com.cms.entity;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
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

    @Column(name = "url")
    private String url;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "completed_date")
    private Instant completedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qa_id", referencedColumnName = "id")
    private QA qa;

    @OneToMany(mappedBy = "category", cascade = CascadeType.DETACH)
    private List<Idea> idea;
}