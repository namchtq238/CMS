package com.cms.service;

import com.cms.controller.request.CategoryReq;
import com.cms.controller.response.CategoryRes;
import com.cms.controller.service.CategoryService;
import com.cms.database.CategoryRepo;
import com.cms.entity.Category;
import com.cms.mapper.Mapper;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    Mapper mapper;

    @Override
    public List<CategoryRes> categoryList() {
        List<Category> list = categoryRepo.findAll();
        return list.stream().map(category -> mapper.entityCategoryToCategoryRes(category)).collect(Collectors.toList());
    }

    @Override
    public CategoryRes addCategory(CategoryReq categoryReq) {
        Category category = new Category();
        category.setActive(categoryReq.isActive());
        category.setCreatedDate(Instant.now());
        category.setDescription(categoryReq.getName());
        category = categoryRepo.save(category);
        categoryRepo.flush();
        return mapper.entityCategoryToCategoryRes(category);
    }

    @Override
    public CategoryRes getACategory(Long id) {
        Optional<Category> opt = categoryRepo.findById(id);
        if (opt.isPresent()){
            Category category = opt.get();
            return mapper.entityCategoryToCategoryRes(category);
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

            return mapper.entityCategoryToCategoryRes(category);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepo.findById(id).orElseThrow();
        if (category.isActive() == false){
            categoryRepo.delete(category);
        }
        else throw new MessageDescriptorFormatException("Category is active, cannot delete");
    }
}
