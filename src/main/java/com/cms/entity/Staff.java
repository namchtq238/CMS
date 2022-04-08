package com.cms.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;

    @OneToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "staff", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Idea> idea;

    @OneToMany(mappedBy = "staff", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Likes> like;

    @OneToMany(mappedBy = "staff", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Comment> comments;
}