package com.cms.config.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseHelper {
    public ResponseEntity<?> successResp(Object object, HttpStatus httpStatus) {
        return new ResponseEntity<>(new Response(object, true, httpStatus.value(), null), httpStatus);
    }
    public ResponseEntity<?> infoResp(String ex, HttpStatus httpStatus){
        return new ResponseEntity<>(new Response(null, false, httpStatus.value(), ex), httpStatus );
    }
}
