package com.cms.controller.api;

import com.cms.constants.ERole;
import com.cms.controller.request.DownloadReq;
import com.cms.controller.request.UploadReq;
import com.cms.controller.service.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/ideas")
public class IdeaController {
    @Autowired
    IdeaService ideaService;


    @GetMapping("")
    public ResponseEntity<?> getListIdea(@RequestParam(name = "departmentId") Long id,
                                         @RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "5") Integer size){
        try{
            return ResponseEntity.ok(ideaService.findIdea(id,page,size));
        }catch (Exception ex){
            return ResponseEntity.internalServerError().body(String.format("We have something wrong with %s",ex.getCause()));
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> uploadIdea(@ModelAttribute @Valid UploadReq req){
        try {
            return ResponseEntity.ok(ideaService.uploadDocumentInScheduled(req));
        }catch (Exception ex){
            return ResponseEntity.internalServerError().body("Upload fail, please try again!" +
                    " Error code: " + ex.getMessage());
        }
    }

    @GetMapping("/download-zip")
    public ResponseEntity<?> downloadAllFile(HttpServletResponse response, @RequestBody DownloadReq req){
        try{
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=download.zip");
            response.setStatus(HttpServletResponse.SC_OK);
            if(req.getRole().equals(ERole.ADMIN.getTypeInStr())) ideaService.downloadFile(req, response);
            return ResponseEntity.ok("ok");
        }catch (Exception e){
            throw new RuntimeException("Internal Error!" + e.getMessage());
        }
    }
}
