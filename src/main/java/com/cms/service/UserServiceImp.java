package com.cms.service;

import com.cms.constants.ERole;
import com.cms.controller.request.UserInfoReq;
import com.cms.controller.request.UserReq;
import com.cms.controller.response.UserInfoRes;
import com.cms.controller.service.UserService;
import com.cms.database.UserRepository;
import com.cms.entity.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @SneakyThrows
    @Transactional(rollbackOn = RuntimeException.class)
    public void registerUser(UserReq req) {
        User user = new User();

        if(userRepo.existsByUserName(req.getUsername())) throw new Exception(String.format("username %s already existed"));
        user.setUserName(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(ERole.STAFF.getValue());

        userRepo.save(user);
    }

    @Override
    @SneakyThrows
    @Transactional(rollbackOn = RuntimeException.class)
    public UserInfoRes updateUserInfo(UserInfoReq req) {
        Optional<User> userOpt = userRepo.findById(req.getId());

        if(userOpt.isEmpty()) throw new Exception("can't find user");
        User user = userOpt.get();

        user.setName(req.getName());
        user.setAddress(req.getAddress());
        user.setEmail(req.getEmail());

        userRepo.save(user);

        return UserInfoRes.builder()
                .name(req.getName())
                .address(req.getAddress())
                .username(user.getUserName())
                .email(req.getEmail())
                .build();
    }
}
