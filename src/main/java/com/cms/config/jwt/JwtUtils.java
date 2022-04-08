package com.cms.config.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    private final static Logger Logger = LoggerFactory.getLogger(JwtUtils.class);

    private  String jwtSecret;
}
