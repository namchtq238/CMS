package com.cms.service;

import com.cms.controller.request.StaffReq;
import com.cms.controller.response.StaffRes;
import com.cms.controller.service.StaffService;
import com.cms.controller.service.UserService;
import com.cms.database.StaffRepo;
import com.cms.database.UserRepository;
import com.cms.entity.Staff;
import com.cms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StaffServiceImp implements StaffService {
    @Autowired
    UserService userService;
    @Autowired
    StaffRepo staffRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Override
    public Staff getCurrentStaff() {
        User user = userService.getCurrentUser();
        return staffRepo.getStaffByUser(user);
    }

    @Override
    public List<StaffRes> getAllStaff() {
        List<Staff> staffList = staffRepo.getAll();
        return staffList.stream().map(this::getStaffRes).collect(Collectors.toList());

    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public StaffRes createNewStaff(StaffReq staffReq) {
        User user = new User();
        Staff staff = new Staff();
        user.setPassword(passwordEncoder.encode(staffReq.getPassword()));
        user.setUserName(staffReq.getUsername());
        user.setAddress(staffReq.getAddress());
        user.setEmail(staffReq.getEmail());
        user.setRole(1);
        user.setName(staffReq.getName());
        userRepository.save(user);
        staff.setPosition(staffReq.getPosition());
        staff.setUser(user);
        staffRepo.save(staff);
        return getStaffRes(staff);
    }

    @Override
    public StaffRes getStaff(Long id) {
        Optional<Staff> opt = staffRepo.findById(id);
        if (opt.isEmpty()) throw new RuntimeException("Not Found");
        Staff staff = opt.get();
        return getStaffRes(staff);
    }

    private StaffRes getStaffRes(Staff staff) {
        StaffRes res = new StaffRes();
        res.setStaffId(staff.getId());
        res.setPosition(staff.getPosition());
        res.setUserId(staff.getUser().getId());
        res.setName(staff.getUser().getName());
        res.setAddress(staff.getUser().getAddress());
        res.setEmail(staff.getUser().getEmail());
        res.setRole(staff.getUser().getRole());
        res.setUsername(staff.getUser().getUserName());

        return res;
    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public boolean delete(Long id) {
        Optional<Staff> opt = staffRepo.findById(id);
        if (opt.isEmpty()) throw new RuntimeException("Not Found");
        userRepository.delete(opt.get().getUser());
        return true;
    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public StaffRes update(Long id, StaffReq staffReq) {
        Optional<Staff> opt = staffRepo.findById(id);
        if (opt.isEmpty()) throw new RuntimeException("Not Found");
        Staff staff = opt.get();
        staff.setPosition(staff.getPosition());
        Optional<User> optionalUser = userRepository.findById(staff.getUser().getId());
        if (optionalUser.isEmpty()) throw new RuntimeException("Not Found");
        User user = optionalUser.get();
        user.setName(staffReq.getName());
        user.setEmail(staffReq.getEmail());
        user.setRole(staffReq.getRole());
        if(!staffReq.getPassword().isBlank()) user.setUserName(staffReq.getUsername());
        user.setPassword(passwordEncoder.encode(staffReq.getPassword()));
        user.setAddress(staffReq.getAddress());

        userRepository.save(user);
        staffRepo.save(staff);

        return getStaffRes(staff);
    }
}
