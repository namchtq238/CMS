package com.cms.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position")
    private String position;

    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.DETACH)
    private List<Idea> idea;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.DETACH)
    private List<Like> like;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.DETACH)
    private List<Comment> comments;
}