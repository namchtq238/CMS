package com.cms.database;

import com.cms.database.converter.IdeaConverter;
import com.cms.database.converter.IdeaDetailConverter;
import com.cms.entity.Idea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
    @Query(value = "select * from idea where id = ?1 and staff_id = ?2", nativeQuery = true)
    Optional<Idea> findByIdeaIdAndStaffId(Long id, Long staffId);

    @Query(value = "SELECT idea.id AS id, " +
            "idea.time_up AS timeUp, " +
            "idea.description AS description, " +
            "idea.user_id AS staffId, " +
            "idea.category_id AS categoryId, " +
            "idea.department_id AS departmentId, " +
            "idea.NAME AS ideaName, " +
            "document.url AS url, " +
            "like_detail.is_like as likeStatus, " +
            "`users`.name as creatorName " +
            "FROM `idea` " +
            "LEFT JOIN `departments` " +
            "ON `idea`.department_id = `departments`.id " +
            "LEFT JOIN `document` on `idea`.document_id = `document`.id " +
            "LEFT JOIN `like_detail` on `idea`.id = `like_detail`.idea_id " +
            "LEFT JOIN `users` on `idea`.user_id = `users`.id " +
            "WHERE 1 = 1 " +
            "AND departments.id = ?1 " +
            "GROUP BY idea.id", nativeQuery = true)
    Page<IdeaConverter> findByCategoryId(Long departmentId, Pageable pageable);
}
