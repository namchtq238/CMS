package com.cms.controller.service;

import com.cms.controller.request.StaffReq;
import com.cms.controller.response.StaffRes;
import com.cms.entity.User;

import java.util.List;

public interface StaffService {

    List<StaffRes> getAllStaff();

    StaffRes createNewStaff(StaffReq staffReq);

    StaffRes getStaff(Long id);

    void delete(Long id);

    StaffRes update(Long id, StaffReq staffReq);

}
