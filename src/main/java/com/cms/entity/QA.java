package com.cms.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "qa")
public class QA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "department_id")
    private String departmentId;

    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "qa", cascade = CascadeType.DETACH)
    private List<Category> category;
}