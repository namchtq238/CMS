package com.cms.controller.api;

import com.cms.controller.request.CommentReq;
import com.cms.controller.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping()
    public ResponseEntity<?> getAllComment(@RequestParam Long ideaId){
        try {
            return ResponseEntity.ok().body(commentService.getAllComment(ideaId));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @PostMapping()
    public ResponseEntity<?> postAComment(@RequestBody CommentReq commentReq){
        try {
            return ResponseEntity.ok().body(commentService.postComment(commentReq));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


}
