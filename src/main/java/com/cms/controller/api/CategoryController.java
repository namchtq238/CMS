package com.cms.controller.api;

import com.cms.config.dto.ResponseHelper;
import com.cms.controller.request.CategoryReq;
import com.cms.controller.service.CategoryService;
import com.google.api.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ResponseHelper responseHelper;

    @GetMapping()
    public ResponseEntity<?> getAllCategory(){
        try {
            return responseHelper.successResp(categoryService.categoryList(), HttpStatus.OK);
        }
        catch (Exception e){
            return responseHelper.infoResp(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<?> addCategory(@RequestBody CategoryReq categoryReq){
        try {
            return responseHelper.successResp(categoryService.addCategory(categoryReq), HttpStatus.CREATED);
        }
        catch (Exception e){
            return responseHelper.infoResp(e.getMessage(),HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getACategory(@PathVariable Long id){
        try {
            return responseHelper.successResp(categoryService.getACategory(id), HttpStatus.OK);
        }
        catch (Exception e){
            return responseHelper.infoResp(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putACategory(@PathVariable Long id, @RequestBody CategoryReq categoryReq){
        try {
            return responseHelper.successResp(categoryService.putACategory(id, categoryReq), HttpStatus.CREATED);
        }
        catch (Exception e){
            return responseHelper.infoResp(e.getMessage(),HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            return responseHelper.successResp(categoryService.delete(id), HttpStatus.OK);
        }
        catch (Exception e){
            return responseHelper.infoResp(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
