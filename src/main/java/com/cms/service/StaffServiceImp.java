package com.cms.service;
import com.cms.constants.ERole;
import com.cms.controller.request.StaffReq;
import com.cms.controller.response.StaffRes;
import com.cms.controller.service.StaffService;
import com.cms.controller.service.UserService;
import com.cms.database.UserRepository;
import com.cms.entity.User;
import com.cms.mapper.Mapper;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
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
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Mapper mapper;
    @Override
    public List<StaffRes> getAllStaffAndQa() {
        List<User> userList = userRepository.findByRoleStaffAndQa();
        return userList.stream().map(entity -> mapper.entityStaffToStaffRes(entity)).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public StaffRes createNewStaff(StaffReq staffReq) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(staffReq.getPassword()));
        user.setUserName(staffReq.getUsername());
        user.setAddress(staffReq.getAddress());
        user.setEmail(staffReq.getEmail());
        user.setRole(ERole.STAFF.getValue());
        user.setName(staffReq.getName());
        userRepository.save(user);
        userRepository.save(user);
        StaffRes res = mapper.entityStaffToStaffRes(user);
        return res;
    }

    @Override
    public StaffRes getStaff(Long id) {
        Optional<User> userOpt = userRepository.getByIdAndRole(id, ERole.STAFF.getValue());
        if(userOpt.isEmpty()) return null;
        User user = userOpt.get();

        return mapper.entityStaffToStaffRes(user);
    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public void delete(Long id) {
        Optional<User> opt = userRepository.getByIdAndRole(id, ERole.STAFF.getValue());
        if (opt.isEmpty()) throw new MessageDescriptorFormatException("Could not found Staff with id: " + id);
        User user = opt.get();
        userRepository.delete(user);
    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public StaffRes update(Long id, StaffReq staffReq) {
        Optional<User> opt = userRepository.getByIdAndRole(id, ERole.STAFF.getValue());
        if (opt.isEmpty()) throw new RuntimeException("Could not found Staff with id: " + id);
        User user = opt.get();

        user.setName(staffReq.getName());
        user.setEmail(staffReq.getEmail());
        user.setRole(staffReq.getRole());
        user.setUserName(staffReq.getUsername());
        user.setAddress(staffReq.getAddress());
        userRepository.save(user);

        return mapper.entityStaffToStaffRes(user);
    }
}
