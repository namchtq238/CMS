package com.cms.service;

import com.cms.config.PaginationT;
import com.cms.controller.response.DepartmentResponse;
import com.cms.controller.service.DepartmentService;
import com.cms.database.DepartmentsRepo;
import com.cms.database.converter.DepartmentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DepartmentServiceImp implements DepartmentService {
    @Autowired
    DepartmentsRepo departmentsRepo;

    @Override
    public PaginationT<DepartmentResponse> getListDepartment(String keyWord, Integer page, Integer size) {
        PaginationT<DepartmentResponse> result = new PaginationT<>();
        Sort sort = Sort.by("id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<DepartmentConverter> data = departmentsRepo.getListDepartments(keyWord, pageable);

        result.setTotal(data.getTotalElements());
        result.setItems(data.stream().map(converter -> {
            DepartmentResponse res = new DepartmentResponse();
            if(converter == null) return null;
            res.setId(converter.getId());
            res.setName(converter.getName());
            res.setQaName(converter.getQaName());
            return res;
        }).collect(Collectors.toList()));
        return result;
    }
}
