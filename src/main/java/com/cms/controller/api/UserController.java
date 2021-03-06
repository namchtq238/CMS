package com.cms.controller.api;

import com.cms.config.dto.ResponseHelper;
import com.cms.config.jwt.JwtUtils;
import com.cms.config.jwt.UserDetailsImpl;
import com.cms.controller.request.ChangePasswordReq;
import com.cms.controller.request.UserInfoReq;
import com.cms.controller.request.UserRegisterReq;
import com.cms.controller.request.UserReq;
import com.cms.controller.response.LoginResponse;
import com.cms.controller.response.UserInfoRes;
import com.cms.controller.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticate;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ResponseHelper responseHelper;
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Validated UserReq loginRequest){
        try{
        Authentication authentication = authenticate.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String roles = userDetails.getRole();
        return responseHelper.successResp(new LoginResponse(jwt,
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles, userDetails.getUser().getId()), HttpStatus.OK);
        }
        catch (Exception exception){
            return ResponseEntity.internalServerError().body(String.format("Code Error: %s ", exception.getLocalizedMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createNewAccount(@Validated @RequestBody UserRegisterReq user){
        try{
            userService.registerUser(user);
            return responseHelper.successResp("success", HttpStatus.OK);
        }
        catch (RuntimeException ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateInfoUser(@RequestBody UserInfoReq req){
        try{
            UserInfoRes user = userService.updateUserInfo(req);
            return responseHelper.successResp(user,HttpStatus.OK);
        }catch (RuntimeException ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordReq req){
        try{
            userService.updatePassword(req);
            return responseHelper.successResp("Success",HttpStatus.OK);
        }catch (Exception ex){
            return ResponseEntity.internalServerError().body(ex.getLocalizedMessage());
        }
    }
}
