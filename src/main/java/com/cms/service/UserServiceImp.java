package com.cms.service;

import com.cms.config.jwt.UserDetailsImpl;
import com.cms.config.jwt.JwtUtils;
import com.cms.constants.ERole;
import com.cms.controller.request.ChangePasswordReq;
import com.cms.controller.request.UserInfoReq;
import com.cms.controller.request.UserReq;
import com.cms.controller.response.UserInfoRes;
import com.cms.controller.service.UserService;
import com.cms.database.StaffRepo;
import com.cms.database.UserRepository;
import com.cms.entity.Staff;
import com.cms.entity.User;
import lombok.SneakyThrows;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    StaffRepo staffRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @SneakyThrows
    @Transactional(rollbackOn = Exception.class)
    public void registerUser(@Valid UserReq req) throws RuntimeException {
        try {
            User user = new User();
            if (userRepo.existsByUserName(req.getUsername()))
                throw new MessageDescriptorFormatException(String.format("Email %s already existed", req.getUsername()));
            user.setUserName(req.getUsername());
            user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setRole(ERole.STAFF.getValue());

            userRepo.save(user);
            Staff staff = new Staff();
            staff.setUser(user);
            staffRepo.save(staff);
        } catch (Exception e) {
            throw new MessageDescriptorFormatException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    @Transactional(rollbackOn = RuntimeException.class)
    public UserInfoRes updateUserInfo(UserInfoReq req) {
        Optional<User> userOpt = userRepo.findById(req.getId());

        if (userOpt.isEmpty()) throw new MessageDescriptorFormatException("can't find user");
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

    @Override
    @SneakyThrows
    public void updatePassword(ChangePasswordReq req) {
        Optional<User> userOpt = userRepo.findByUserName(req.getUsername());
        User user = userOpt.get();
        if (userOpt.isEmpty()) throw new MessageDescriptorFormatException("username not found");
        if (passwordEncoder.matches(req.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        } else throw new MessageDescriptorFormatException("new password does not match with raw password");
        userRepo.save(user);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUser();
    }
}
