package com.cms.database;

import com.cms.entity.Staff;
import com.cms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Long> {
    Staff getStaffByUser(User user);
}
