package com.cms.database;

import com.cms.entity.Staff;
import com.cms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Long> {
    Staff getStaffByUser(User user);
    @Query(nativeQuery = true, value = "SELECT * FROM staff")
    List<Staff> getAll();
}
