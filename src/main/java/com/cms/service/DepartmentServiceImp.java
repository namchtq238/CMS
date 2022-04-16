package com.cms.service;

import com.cms.config.PaginationT;
import com.cms.constants.ERole;
import com.cms.controller.request.DepartmentReq;
import com.cms.controller.response.DepartmentResponse;
import com.cms.controller.service.DepartmentService;
import com.cms.controller.service.UserService;
import com.cms.database.DepartmentsRepo;
import com.cms.database.UserRepository;
import com.cms.database.converter.DepartmentConverter;
import com.cms.entity.Departments;
import com.cms.entity.User;
import com.cms.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImp implements DepartmentService {
    @Autowired
    DepartmentsRepo departmentsRepo;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepo;

    @Autowired
    Mapper mapper;

    @Override
    public PaginationT<DepartmentResponse> getListDepartment(String keyWord, Integer page, Integer size) {
        PaginationT<DepartmentResponse> result = new PaginationT<>();
        Sort sort = Sort.by("id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<DepartmentConverter> data = departmentsRepo.getListDepartments(keyWord, pageable);

        result.setTotal(data.getTotalElements());
        result.setItems(data.stream().map(converter -> mapper.converterDepartmentToDepartmentRes(converter)).collect(Collectors.toList()));
        return result;
    }

    @Override
    public DepartmentResponse getDepartmentDetail(Long id) {
        Departments departments = departmentsRepo.findById(id).orElseThrow(() -> new RuntimeException("Could not find department with id" + id));
        DepartmentResponse response= mapper.entityToDepartmentRes(departments);
        Optional<User> userOpt = userRepo.getByIdAndRole(departments.getUserId(), ERole.QA.getValue());
        if(userOpt.isEmpty()) throw new RuntimeException("Cannot find QA with id" + departments.getUserId());
        response.setQaName(userOpt.get().getName());
        return response;
    }

    @Override
    public DepartmentResponse addDepartment(DepartmentReq departmentReq) {
        Departments departments = new Departments();

        departments.setName(departmentReq.getName());
        departments.setStartDate(Instant.parse(departmentReq.getStartDate()));
        departments.setUserId(departmentReq.getId());
        departments.setClosureDate(Instant.parse(departmentReq.getClosureDateIdea()));
        departments.setClosureDateIdea(Instant.parse(departmentReq.getClosureDateIdea()));

        departmentsRepo.save(departments);
        DepartmentResponse response = new DepartmentResponse();
        response.setStartDate(Instant.parse(departmentReq.getStartDate()));
        response.setClosureDateIdea(Instant.parse(departmentReq.getClosureDateIdea()));
        response.setClosureDate(Instant.parse(departmentReq.getClosureDate()));
        response.setName(departmentReq.getName());
        return response;
    }

    @Override
    public DepartmentResponse update(Long id, DepartmentReq departmentReq) {
        Departments departments = departmentsRepo.findById(id).orElseThrow(() -> new RuntimeException("Cannot find department with ID: " + id));

        departments.setName(departmentReq.getName());
        departments.setStartDate(Instant.parse(departmentReq.getStartDate()));
        departments.setClosureDate(Instant.parse(departmentReq.getClosureDate()));
        departments.setClosureDateIdea(Instant.parse(departmentReq.getClosureDateIdea()));
        departmentsRepo.save(departments);

        DepartmentResponse response = new DepartmentResponse();
        response.setStartDate(Instant.parse(departmentReq.getStartDate()));
        response.setClosureDateIdea(Instant.parse(departmentReq.getClosureDateIdea()));
        response.setClosureDate(Instant.parse(departmentReq.getClosureDate()));
        response.setName(departmentReq.getName());
        return response;
    }

}
