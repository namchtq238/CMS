package com.cms.controller.api;

import com.cms.config.DataResponse;
import com.cms.config.dto.ResponseHelper;
import com.cms.controller.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistic")
public class StatisticController {
    @Autowired
    ResponseHelper responseHelper;
    @Autowired
    StatisticService statisticService;
    @GetMapping
    public ResponseEntity<?> getStatistic(){
        try{
            return responseHelper.successResp(statisticService.getListStatistic(), HttpStatus.OK);
        }
        catch (Exception e){
            return responseHelper.infoResp(e.getMessage(), HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
        }
    }
}
