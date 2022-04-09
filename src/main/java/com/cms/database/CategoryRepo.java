package com.cms.database;

import com.cms.controller.response.CategoryRes;
import com.cms.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
//    @Query(nativeQuery = true, value = "SELECT c.created_date as createdDate," +
//            "c.description as description, c.completed_date as completedDate," +
//            "c.active as active, q.id as qa,  FROM category as C" +
//            "LEFT JOIN qa as q on c.qa_id = q.id" +
//            "LEFT JOIN idea as i on c.idea_id = i.id")
    List<Category> getAll();
}
