package com.cms.controller.api;

import com.cms.controller.request.UserInfoReq;
import com.cms.controller.request.UserReq;
import com.cms.controller.response.UserInfoRes;
import com.cms.controller.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createNewAccount(@RequestBody UserReq user){
        try{
            userService.registerUser(user);
            return ResponseEntity.ok("Success");
        }
        catch (RuntimeException ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateInfoUser(@RequestParam Long id, @RequestBody UserInfoReq req){
        try{
            UserInfoRes user = userService.updateUserInfo(id, req);
            return ResponseEntity.ok(user);
        }catch (RuntimeException ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }
}
