package com.cms.database;

import com.cms.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepo extends JpaRepository<Likes, Long> {
    Likes findByStaffIdAndIdeaId(Long staffId, Long ideaId);
    Integer countLikesByIdeaId(Long ideaId);
}
