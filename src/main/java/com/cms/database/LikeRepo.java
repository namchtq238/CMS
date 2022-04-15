package com.cms.database;

import com.cms.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepo extends JpaRepository<Likes, Long> {
    @Query(value = "select * from like_detail where staff_id = ?1 and idea_id = ?2", nativeQuery = true)
    Optional<Likes> findByStaffIdAndIdeaId(Long staffId, Long ideaId);

    Integer countLikesByIdeaId(Long ideaId);

    @Query(value = "select count(id) from like_detail where is_like = ?1 and idea_id = ?2", nativeQuery = true)
    Integer countLikesByIsLikeAndIdeaId(Integer isLike, Long ideaId);

    @Query(value = "select is_like from like_detail where idea_id = ?1 and staff_id = ?2", nativeQuery = true)
    Integer findLikeStatusByIdeaIdAndStaffId(Long ideaId, Long staffId);

    @Modifying
    @Transactional
    @Query(value = "insert into like_detail(idea_id, staff_id, is_like, created_date) values(?1, ?2, 1, ?3) ", nativeQuery = true)
    void saveLikeByStaffIdAndIdeaId(Long staffId, Long ideaId, Instant now);
}
