package com.cms.entity;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Table(name = "idea")
@Entity
public class Idea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "time_up")
    private Instant timeUp;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "idea", cascade = CascadeType.DETACH)
    private List<Like> like;

    @OneToMany(mappedBy = "idea", cascade = CascadeType.DETACH)
    private List<Comment> comments;
}