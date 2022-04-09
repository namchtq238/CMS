package com.cms.controller.service;

import com.cms.controller.request.CategoryReq;
import com.cms.controller.response.CategoryRes;

import java.util.List;

public interface CategoryService {
    List<CategoryRes> categoryList();
    CategoryReq addCategory(CategoryReq categoryReq);
    CategoryRes getACategory(Long id);
    CategoryRes putACategory(Long id, CategoryReq categoryReq);
    void delete(Long id);
}
