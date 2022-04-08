package com.cms.database;

import com.cms.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepo extends JpaRepository<Likes, Long> {
}
