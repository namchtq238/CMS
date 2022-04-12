package com.cms.database;

import com.cms.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> getAllByIdeaId(Long ideaId);
    @Query(value = "SELECT count(comment.id) from comment where idea_id = ?", nativeQuery = true)
    Integer countCommentForDetailIdea(Long ideaId);
}
