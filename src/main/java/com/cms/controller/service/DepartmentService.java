package com.cms.controller.service;

import com.cms.config.PaginationT;
import com.cms.controller.response.DepartmentResponse;

import java.io.File;

public interface DepartmentService {
    PaginationT<DepartmentResponse> getListDepartment(String keyWord, Integer page, Integer size);

    DepartmentResponse getDepartmentDetail(Long id);

}
