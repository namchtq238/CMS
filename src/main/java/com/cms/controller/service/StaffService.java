package com.cms.controller.service;

import com.cms.controller.request.StaffReq;
import com.cms.controller.response.StaffRes;
import com.cms.entity.Staff;

import java.util.List;

public interface StaffService {
    Staff getCurrentStaff();

    List<StaffRes> getAllStaff();

    StaffRes createNewStaff(StaffReq staffReq);

    StaffRes getStaff(Long id);

    boolean delete(Long id);

    StaffReq update(Long id, StaffReq staffReq);

}
