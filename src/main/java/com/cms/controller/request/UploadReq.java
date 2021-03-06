package com.cms.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.Instant;

@Getter
@Setter
public class UploadReq {
    Long userId;
    Long departmentId;
    String name;
    String startDate;
    String endDate;
    MultipartFile file;
    Long categoryId;
    String description;
}
