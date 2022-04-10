package com.cms.controller.api;

import com.cms.controller.request.CategoryReq;
import com.cms.controller.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<?> getAllCategory(){
        try {
            return ResponseEntity.ok().body(categoryService.categoryList());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> addCategory(@RequestBody CategoryReq categoryReq){
        try {
            return ResponseEntity.ok().body(categoryService.addCategory(categoryReq));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getACategory(@PathVariable Long id){
        try {
            return ResponseEntity.ok().body(categoryService.getACategory(id));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putACategory(@PathVariable Long id, @RequestBody CategoryReq categoryReq){
        try {
            return ResponseEntity.ok().body(categoryService.putACategory(id, categoryReq));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            categoryService.delete(id);
            return ResponseEntity.ok("Delete Success");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
