package com.cms.controller.api;

import com.cms.config.dto.ResponseHelper;
import com.cms.controller.request.ChangeStatusReq;
import com.cms.controller.service.LikesService;
import com.cms.controller.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/like")
public class LikesController {
    private static Logger logger = LoggerFactory.getLogger(LikesController.class);

    @Autowired
    LikesService likesService;

    @Autowired
    StaffService staffService;

    @Autowired
    ResponseHelper responseHelper;

    @PostMapping("/change-status")
    public ResponseEntity<?> changeStatusLikes(@RequestBody ChangeStatusReq changeStatusReq){
        try{
            likesService.changeStatusLike(changeStatusReq);
            return responseHelper.successResp("OK", HttpStatus.OK);
        }catch (Exception ex){
            logger.error(ex.getMessage());
            return responseHelper.successResp(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> countLike(@RequestParam Long id ){
        try {
            return responseHelper.successResp(likesService.countLike(id),HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Request failed");
        }
    }

}
