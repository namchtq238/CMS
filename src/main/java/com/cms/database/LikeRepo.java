package com.cms.database;

import com.cms.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepo extends JpaRepository<Likes, Long> {
    @Query(value = "select * from like_detail where staff_id = ?1 and idea_id = ?2", nativeQuery = true)
    Likes findByStaffIdAndIdeaId(Long staffId, Long ideaId);
    Integer countLikesByIdeaId(Long ideaId);
    @Query(value = "select count(id) from like_detail where is_like = ?1 and idea_id = ?2", nativeQuery = true)
    Integer countLikesByIsLikeAndIdeaId(Integer isLike, Long ideaId);
//    List<Likes> findByIdeaId(Long ideaId);
}
