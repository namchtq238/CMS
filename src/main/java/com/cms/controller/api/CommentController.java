package com.cms.controller.api;

import com.cms.config.dto.ResponseHelper;
import com.cms.controller.request.CommentReq;
import com.cms.controller.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    ResponseHelper responseHelper;
    @Autowired
    CommentService commentService;

    @GetMapping()
    public ResponseEntity<?> getAllComment(@RequestParam Long ideaId){
        try {
            return responseHelper.successResp(commentService.getAllComment(ideaId), HttpStatus.OK);

        }
        catch (Exception e){
            return responseHelper.infoResp(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping()
    public ResponseEntity<?> postAComment(@RequestBody CommentReq commentReq){
        try {
            return responseHelper.successResp(commentService.postComment(commentReq), HttpStatus.CREATED);
        }
        catch (Exception e){
            return responseHelper.infoResp(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
