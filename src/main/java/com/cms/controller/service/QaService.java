package com.cms.controller.service;

import com.cms.controller.request.StaffReq;

public interface QaService {
    boolean updateQa(Long id, StaffReq staffReq);
}
