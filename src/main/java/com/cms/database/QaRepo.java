package com.cms.database;

import com.cms.entity.QA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QaRepo extends JpaRepository<QA, Long> {
    QA getByDepartmentsId(Long departmentId);
}
