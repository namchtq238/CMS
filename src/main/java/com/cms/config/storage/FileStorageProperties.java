package com.cms.config.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageProperties {
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Bean
    public String getUploadDir() {
        return uploadDir;
    }
//    @Bean
//    public void setUploadDir(String uploadDir) {
//        this.uploadDir = uploadDir;
//    }
}