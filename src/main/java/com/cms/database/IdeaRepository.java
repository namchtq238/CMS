package com.cms.database;

import com.cms.database.converter.IdeaConverter;
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

    @Query(value = "SELECT i.time_up as timeUp," +
            "i.description as description, " +
            "i.staff_id as staffId, " +
            "i.category_id as category, " +
            "GROUP_CONCAT(DISTINCT l.id) as detailLikes, " +
            "count(l.id) as totalLike, " +
            "GROUP_CONCAT(DISTINCT m.id) as detailComment," +
            "count(m.id) as totalComment " +
            "FROM idea as i " +
            "LEFT JOIN likes as l on i.id = l.idea_id " +
            "LEFT JOIN comment as m on i.id = m.idea_id " +
            "GROUP BY i.id ", nativeQuery = true)
    Page<IdeaConverter> findAllIdea(Pageable pageable);
}
