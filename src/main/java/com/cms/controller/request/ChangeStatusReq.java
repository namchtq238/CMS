package com.cms.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeStatusReq {
    Long staffId;
    Long ideaId;
    Integer status;
}
