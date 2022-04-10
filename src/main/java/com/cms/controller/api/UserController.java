package com.cms.controller.api;

import com.cms.config.jwt.JwtUtils;
import com.cms.config.jwt.UserDetailsImpl;
import com.cms.controller.request.ChangePasswordReq;
import com.cms.controller.request.UserInfoReq;
import com.cms.controller.request.UserReq;
import com.cms.controller.response.LoginResponse;
import com.cms.controller.response.UserInfoRes;
import com.cms.controller.service.UserService;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticate;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Validated UserReq loginRequest){
        try{
        Authentication authentication = authenticate.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String roles = userDetails.getRole();
        return ResponseEntity.ok(new LoginResponse(jwt,
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
        }
        catch (Exception exception){
            return ResponseEntity.internalServerError().body(String.format("Code Error: %s ", exception.getLocalizedMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createNewAccount(@Validated @RequestBody UserReq user){
        try{
            userService.registerUser(user);
            return ResponseEntity.ok("Success");
        }
        catch (RuntimeException ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateInfoUser(@RequestBody UserInfoReq req){
        try{
            UserInfoRes user = userService.updateUserInfo(req);
            return ResponseEntity.ok(user);
        }catch (RuntimeException ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordReq req){
        try{
            userService.updatePassword(req);
            return ResponseEntity.ok("Success");
        }catch (Exception ex){
            return ResponseEntity.internalServerError().body(ex.getLocalizedMessage());
        }
    }
}
