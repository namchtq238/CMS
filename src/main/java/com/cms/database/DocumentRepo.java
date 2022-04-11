package com.cms.database;

import com.cms.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {
    List<Document> findByNameIn(List<String> list);
}
