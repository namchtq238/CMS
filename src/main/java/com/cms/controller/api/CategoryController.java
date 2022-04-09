package com.cms.controller.api;

import com.cms.controller.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(){
        try {
            return ResponseEntity.ok().body(categoryService.categoryList());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("An error occurred while fetching category");
        }
    }
//    @PostMapping("/category")
//    public ResponseEntity<?> addCategory(){
//        try {
//            return ResponseEntity.created().body();
//        }
//        catch (Exception e){
//            return ResponseEntity.internalServerError().body("An error occurred while posting category");
//        }
//    }
}
