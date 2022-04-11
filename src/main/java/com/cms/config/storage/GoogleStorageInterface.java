package com.cms.config.storage;


import com.google.api.services.drive.model.File;

public interface GoogleStorageInterface {
    public File upLoadFile(String fileName, String filePath, String mimeType);
}
