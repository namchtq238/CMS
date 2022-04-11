package com.cms.service;

import com.cms.controller.request.CategoryReq;
import com.cms.controller.response.CategoryRes;
import com.cms.controller.response.ResponseWrapper;
import com.cms.controller.service.CategoryService;
import com.cms.database.CategoryRepo;
import com.cms.entity.Category;
import com.cms.entity.Idea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public List<CategoryRes> categoryList() {
        List<Category> list = categoryRepo.findAll();
        return list.stream().map(category -> {
            CategoryRes res = new CategoryRes();
            res.setActive(category.isActive());
            res.setCreatedDate(category.getCreatedDate().toString());
            res.setName(category.getDescription());
            res.setId(category.getId());
            return res;
        }).collect(Collectors.toList());
    }

    @Override
    public CategoryReq addCategory(CategoryReq categoryReq) {
        Category category = new Category();
        category.setActive(categoryReq.isActive());
        category.setCreatedDate(Instant.now());
        category.setDescription(categoryReq.getName());
        categoryRepo.save(category);
        return categoryReq;
    }

    @Override
    public CategoryRes getACategory(Long id) {
        Optional<Category> opt = categoryRepo.findById(id);
        if (opt.isPresent()){
            Category category = opt.get();
            CategoryRes res = new CategoryRes();
            res.setCreatedDate(category.getCreatedDate().toString());
            res.setName(category.getDescription());
            res.setActive(category.isActive());
            return res;
        }
        return null;
    }

    @Override
    public CategoryRes putACategory(Long id, CategoryReq categoryReq) {
        Optional<Category> opt = categoryRepo.findById(id);
        if (opt.isPresent()){
            Category category = opt.get();
            category.setDescription(categoryReq.getName());
            category.setActive(categoryReq.isActive());
            category.setCreatedDate(Instant.now());
            categoryRepo.save(category);
            CategoryRes res = new CategoryRes();
            res.setCreatedDate(category.getCreatedDate().toString());
            res.setName(category.getDescription());
            res.setActive(category.isActive());
            return res;
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isPresent()) {
            categoryRepo.delete(category.get());
            return true;
        }
        return false;
    }
}
