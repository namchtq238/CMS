package com.cms.database;

import com.cms.entity.QA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QaRepo extends JpaRepository<QA, Long> {
}
