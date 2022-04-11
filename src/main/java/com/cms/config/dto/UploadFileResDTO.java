package com.cms.config.dto;

import com.cms.entity.Document;
import lombok.*;

@Data
@NoArgsConstructor
public class UploadFileResDTO {
    private String fileName;
    private String url;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public UploadFileResDTO(String fileName, String url, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.url = url;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
    // Getters and Setters (Omitted for brevity)
}

