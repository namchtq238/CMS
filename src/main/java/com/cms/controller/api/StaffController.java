package com.cms.controller.api;

import com.cms.config.dto.ResponseHelper;
import com.cms.controller.request.StaffReq;
import com.cms.controller.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    ResponseHelper responseHelper;
    @Autowired
    StaffService staffService;

    @GetMapping()
    public ResponseEntity<?> getallStaff() {
        try {
            return responseHelper.successResp(staffService.getAllStaff(), HttpStatus.OK);
        } catch (Exception e) {
            return responseHelper.infoResp(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<?> addNewStaff(@RequestBody StaffReq staffReq) {
        try {
            return responseHelper.successResp(staffService.createNewStaff(staffReq), HttpStatus.CREATED);
        } catch (Exception e) {
            return responseHelper.infoResp(e.getMessage(), HttpStatus.NO_CONTENT);
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getStaff(@PathVariable Long id){
        try {
            return responseHelper.successResp(staffService.getStaff(id), HttpStatus.OK);
        } catch (Exception e) {
            return responseHelper.infoResp(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable Long id, @RequestBody StaffReq staffReq){
        try {
            return responseHelper.successResp(staffService.update(id, staffReq), HttpStatus.CREATED);
        } catch (Exception e) {
            return responseHelper.infoResp(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id){
        try {
            return responseHelper.successResp(staffService.delete(id), HttpStatus.OK);
        }
        catch (Exception e){
            return responseHelper.infoResp(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

}
