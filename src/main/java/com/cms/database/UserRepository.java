package com.cms.database;

import com.cms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    Optional<User> findById(Long id);

    boolean existsByUserName(String username);

    List<User> findByRole(Integer role);

    @Query(value = "select `users`.* from users where `users`.role = 1 or `users`.role = 3", nativeQuery = true)
    List<User> findByRoleStaffAndQa();

    Optional<User> getByIdAndRole(Long id, Integer role);

    @Query(value = "select `users`.* from `users` left join `departments` on `users`.id = `departments`.user_id where `departments`.id = ?1", nativeQuery = true)
    Optional<User> findUserByDepartmentId(Long departmentId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete `users`.*, `document`.*, `like_detail`.*, `comment`.*, `departments`.*, `idea`.* " +
            "from `users` left join `departments` on users.id=departments.user_id " +
            "left join idea on departments.id=idea.department_id " +
            "left join like_detail on like_detail.idea_id=idea.id " +
            "left join comment on idea.id=comment.idea_id " +
            "left join document on users.id=document.user_id " +
            "where users.id=?1")
    void deleteQA(Long id);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete `users`.*, `like_detail`.*, `comment`.*, `idea`.* from `users` " +
            "left join `idea` on users.id=idea.user_id " +
            "left join like_detail on like_detail.user_id=users.id " +
            "left join comment on users.id=comment.user_id " +
            "where users.id=?1")

    void  deleteStaff(Long id);

}
