package com.cms.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DownloadReq {
    String role;
    String startDate;
    String endDate;
}
