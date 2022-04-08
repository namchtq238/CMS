package com.cms.entity;

import javax.persistence.*;

@Entity
@Table(name = "university")

public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "university", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Departments departments;
}