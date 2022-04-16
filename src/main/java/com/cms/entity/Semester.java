package com.cms.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "semest")
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "closure_date")
    private Instant closureDate;
}
