package com.cms.service;

import com.cms.controller.request.StaffReq;
import com.cms.controller.service.QaService;
import com.cms.database.QaRepo;
import com.cms.entity.Staff;
import com.cms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QaSerivceImp implements QaService {
    @Autowired
    QaRepo qaRepo;
    @Override
    public boolean updateQa(Long id, StaffReq staffReq)
    {
        return false;
    }

}

