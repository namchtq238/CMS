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
    @Query(value = "select departments.id as id, " +
            "departments.name as name, " +
            "departments.start_date as startDate," +
            "departments.closure_date_idea as closureDateIdea," +
            "departments.closure_date as closureDate, " +
            "users.name as qaName  " +
            "from departments d " +
            "left join qa on departments.id = qa.department_id " +
            "left join users on qa.user_id = users.id " +
            "where 1 = 1 " +
            "AND(?1 is null or departments.id like %?1% " +
            "OR departments.name like %?1% " +
            "OR users.name like %?1%)", nativeQuery = true)
    Page<DepartmentConverter> getListDepartments(String keyWord, Pageable pageable);

}
