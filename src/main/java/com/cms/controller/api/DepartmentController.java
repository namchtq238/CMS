package com.cms.controller.api;

import com.cms.config.dto.ResponseHelper;
import com.cms.controller.request.DepartmentReq;
import com.cms.controller.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    ResponseHelper responseHelper;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("")
    public ResponseEntity<?> getListDepartment(@RequestParam(name = "key", defaultValue = "") String key,
                                               @RequestParam(name = "page", defaultValue = "0") Integer page,
                                               @RequestParam(name = "size", defaultValue = "5") Integer size){
        try{
            return responseHelper.successResp(departmentService.getListDepartment(key, page, size), HttpStatus.OK);
        }catch (Exception ex){
            return responseHelper.infoResp(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getDepartmentDetail(@RequestParam(name = "departmentId") Long id){
        try{
            return responseHelper.successResp(departmentService.getDepartmentDetail(id),HttpStatus.OK);
        }catch (Exception ex){
            return responseHelper.infoResp(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addNewDepartment(@RequestBody DepartmentReq departmentReq){
        try {
            return responseHelper.successResp(departmentService.addDepartment(departmentReq), HttpStatus.CREATED);
        }
        catch (Exception e){
            return responseHelper.infoResp(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody DepartmentReq departmentReq, @PathVariable Long id){
        try {
            return responseHelper.successResp(departmentService.update(id, departmentReq), HttpStatus.CREATED);
        }
        catch (Exception e){
            return responseHelper.infoResp(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }
}
