package com.cms.database;

import com.cms.controller.response.CategoryRes;
import com.cms.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    @Query(value = "", nativeQuery = true)
    List<Category> findAll();
}
