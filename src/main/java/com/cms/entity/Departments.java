package com.cms.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
public class Departments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Instant startDate;

    private Instant closureDateIdea;

    private Instant closureDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "university_id", referencedColumnName = "id")
    private University university;

    @OneToOne(mappedBy = "departments", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private QA qa;

    @NotNull
    private boolean isDelete = false;
}