package com.cms.config.storage;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import com.cms.config.dto.UploadFileResDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class GoogleStorage {
    @Value("${google_storage.endpoint}")
    private String ENDPOINT;
    @Value("${google_storage.bucketName}")
    private String BUCKET_NAME;
    @Value("${google_storage.projectId}")
    private String PROJECT_ID;

    private ResourceLoader resourceLoader;

    @Autowired
    public GoogleStorage(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public UploadFileResDTO uploadFileToGoogleCloud(MultipartFile file) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:starry-lens-346706-2b23657a2883.json");
        InputStream inputStream = resource.getInputStream();
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(inputStream))
                .build()
                .getService();
        String filename = file.getName().replaceAll(" ", "");
        BlobId blobId = BlobId.of(BUCKET_NAME, filename);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, Files.readAllBytes(convert(file).toPath()));
        UploadFileResDTO dto = new UploadFileResDTO();
        dto.setUrl(ENDPOINT + "/" + BUCKET_NAME + "/" + filename);
        dto.setName(filename);
        return dto;
    }
}
