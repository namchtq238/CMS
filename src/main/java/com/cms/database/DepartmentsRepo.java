package com.cms.database;

import com.cms.database.converter.DepartmentConverter;
import com.cms.entity.Departments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentsRepo extends JpaRepository<Departments, Long> {
    @Query(value = "select id as from departments " +
            "left join", nativeQuery = true)
    Page<DepartmentConverter> getListDepartments(String keyWord, Pageable pageable);

}
