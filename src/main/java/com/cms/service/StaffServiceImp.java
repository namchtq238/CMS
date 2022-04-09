package com.cms.service;

import com.cms.controller.service.StaffService;
import com.cms.controller.service.UserService;
import com.cms.database.StaffRepo;
import com.cms.entity.Staff;
import com.cms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImp implements StaffService {
    @Autowired
    UserService userService;
    @Autowired
    StaffRepo staffRepo;

    @Override
    public Staff getCurrentStaff() {
        User user = userService.getCurrentUser();
        return staffRepo.getStaffByUser(user);
    }
}
