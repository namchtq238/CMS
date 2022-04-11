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
public class Idea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant startDate;

    private Instant timeUp;

    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private Document document;

    private Long departmentId;

    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "idea", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Likes> like;

    @OneToMany(mappedBy = "idea", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Comment> comments;

    private boolean anonymous = false;

}