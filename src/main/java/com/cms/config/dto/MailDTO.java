package com.cms.config.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDTO {
    String from;
    String to;
    String subject;
    String content;
}
