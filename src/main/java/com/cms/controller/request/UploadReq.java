package com.cms.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.time.Instant;

@Getter
@Setter
public class UploadReq {
    Long id;
    Instant startDate;
    Instant endDate;
    File file;
    Long categoryId;
}
