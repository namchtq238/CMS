package com.cms.controller.service;

import com.cms.controller.request.CategoryReq;
import com.cms.controller.response.CategoryRes;
import com.cms.controller.response.ResponseWrapper;

import java.util.List;

public interface CategoryService {
    ResponseWrapper categoryList();
    CategoryReq addCategory(CategoryReq categoryReq);
    CategoryRes getACategory(Long id);
    CategoryRes putACategory(Long id, CategoryReq categoryReq);
    void delete(Long id);
}
