//package com.cms.config;
//import com.cms.config.dto.UploadFileResDTO;
//import com.google.api.services.storage.Storage;
//import com.google.auth.oauth2.ServiceAccountCredentials;
//import com.google.cloud.storage.StorageOptions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//
//@Component
//public class GoogleStorage {
//    @Value("${google_storage.endpoint}")
//    private String ENDPOINT;
//    @Value("${google_storage.bucketName}")
//    private String BUCKET_NAME;
//    @Value("${google_storage.projectId}")
//    private String PROJECT_ID;
//
//    private ResourceLoader resourceLoader;
//
//    @Autowired
//    public GoogleStorage(ResourceLoader resourceLoader) {
//        this.resourceLoader = resourceLoader;
//    }
//
//    public UploadFileResDTO uploadFileToGoogleCloud(File file) throws IOException {
//        Resource resource = resourceLoader.getResource("classpath:back-office-storage-api-key.json");
//        InputStream inputStream = resource.getInputStream();
//        Storage storage = StorageOptions.newBuilder()
//                .setCredentials(ServiceAccountCredentials.fromStream(inputStream))
//                .build()
//                .getService();
//        String filename = file.getName().replaceAll(" ", "");
//        BlobId blobId = BlobId.of(BUCKET_NAME, filename);
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
//        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
//        UploadFileResDTO dto = new UploadFileResDTO();
//        dto.setUrl(ENDPOINT + "/" + BUCKET_NAME + "/" + filename);
//        dto.setName(filename);
//        return dto;
//    }
//}
