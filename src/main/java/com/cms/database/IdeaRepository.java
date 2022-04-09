package com.cms.database;

import com.cms.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
    Optional<Idea> findById(Long id);
}
