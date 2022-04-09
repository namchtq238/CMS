package com.cms.service;

import com.cms.controller.response.CategoryRes;
import com.cms.controller.service.CategoryService;
import com.cms.database.CategoryRepo;
import com.cms.entity.Category;
import com.cms.entity.Idea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public List<CategoryRes> categoryList() {
        List<Category> list = categoryRepo.getAll();
        List<Long> listIdea = new ArrayList<>();
        return list.stream().map(category -> {
            CategoryRes res = new CategoryRes();
            if (category == null) return null;
            res.setActive(category.isActive());
            res.setCompletedDate(category.getCompletedDate() == null ? null : category.getCompletedDate().toString());
            res.setDescription(category.getDescription());
            res.setCreatedDate(category.getCreatedDate() == null ? null : category.getCreatedDate().toString());
            listIdea.clear();
            for (Idea idea: category.getIdea()
                 ) {
                listIdea.add(idea.getId());
            }
            res.setIdea(listIdea);
            res.setQa(category.getQa().getId());
            return res;
        }).collect(Collectors.toList());
    }
}
