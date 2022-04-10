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
    @Query(value = "select d.id as id, " +
            "d.name as name," +
            "users.name as qaName  " +
            "from departments as d " +
            "left join qa on d.id = qa.department_id " +
            "left join users on qa.user_id = users.id " +
            "where 1 = 1 " +
            "AND(?1 is null or d.id like %?1% " +
            "OR d.name like %?1% " +
            "OR users.name like %?1%)", nativeQuery = true)
    Page<DepartmentConverter> getListDepartments(String keyWord, Pageable pageable);

}
