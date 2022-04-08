package com.cms.entity;

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

    private Instant timeUp;

    private String description;

    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    private Staff staff;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "idea", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Like> like;

    @OneToMany(mappedBy = "idea", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Comment> comments;
}