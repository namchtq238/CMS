package com.cms.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Departments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "university_id", referencedColumnName = "id")
    private University university;

    @OneToOne(mappedBy = "departments", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private QA qa;

    @NotNull
    private boolean isDelete = false;
}