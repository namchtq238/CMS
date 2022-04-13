package com.cms.database;

import com.cms.database.converter.IdeaConverter;
import com.cms.database.converter.IdeaDetailConverter;
import com.cms.entity.Idea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
    Optional<Idea> findById(Long id);

    @Query(value = "SELECT idea.id AS id, " +
            "idea.time_up AS timeUp, " +
            "idea.description AS description, " +
            "idea.staff_id AS staffId, " +
            "idea.category_id AS categoryId, " +
            "idea.department_id AS departmentId, " +
            "idea.NAME AS ideaName, " +
            "document.url AS url, " +
            "detail_like.is_like as likeStatus " +
            "FROM `idea` " +
            "LEFT JOIN `departments` " +
            "ON `idea`.department_id = `departments`.id " +
            "LEFT JOIN `document` on `idea`.document_id = `document`.id " +
            "LEFT JOIN `detail_like` on `idea`.id = `detail_like`.idea_id " +
            "WHERE 1 = 1 " +
            "AND departments.id = ?1 " +
            "GROUP BY idea.id", nativeQuery = true)
    Page<IdeaConverter> findByCategoryId(Long departmentId, Pageable pageable);

}
