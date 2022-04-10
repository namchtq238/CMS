package com.cms.controller.service;

import com.cms.config.PaginationT;
import com.cms.controller.response.DepartmentResponse;
import org.springframework.data.domain.Pageable;

public interface DepartmentService {
    PaginationT<DepartmentResponse> getListDepartment(String keyWord, Integer page, Integer size);

    DepartmentResponse getDepartmentDetail(Long id);
}
