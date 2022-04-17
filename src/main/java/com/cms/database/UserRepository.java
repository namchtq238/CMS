package com.cms.database;

import com.cms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    Optional<User> findById(Long id);

    boolean existsByUserName(String username);

    List<User> findByRole(Integer role);

    Optional<User> getByIdAndRole(Long id, Integer role);

    @Query(value = "select `user`.* from user left join `departments` on `user`.id = `departments`.user_id where `departments`.id = ?1", nativeQuery = true)
    Optional<User> findUserByDepartmentId(Long departmentId);
}
