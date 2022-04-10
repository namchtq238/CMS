package com.cms.controller.api;

import com.cms.config.PaginationT;
import com.cms.controller.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping("")
    public ResponseEntity<?> getListDepartment(@RequestParam(name = "key", defaultValue = "") String key,
                                               @RequestParam(name = "page", defaultValue = "0") Integer page,
                                               @RequestParam(name = "size", defaultValue = "5") Integer size){
        try{
            return ResponseEntity.ok(departmentService.getListDepartment(key,page,size));
        }catch (Exception ex){
            return ResponseEntity.internalServerError().body(ex.getLocalizedMessage());
        }
    }
}
