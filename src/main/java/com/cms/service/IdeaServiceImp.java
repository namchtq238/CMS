package com.cms.service;

import com.cms.config.PaginationT;
import com.cms.config.dto.UploadFileResDTO;
import com.cms.config.storage.FileStorageService;
import com.cms.config.storage.GoogleStorageInterface;
import com.cms.constants.ERole;
import com.cms.controller.request.DownloadReq;
import com.cms.controller.request.UploadReq;
import com.cms.controller.response.ListIdeaRes;
import com.cms.controller.service.IdeaService;
import com.cms.database.CategoryRepo;
import com.cms.database.DocumentRepo;
import com.cms.database.IdeaRepository;
import com.cms.database.UserRepository;
import com.cms.database.converter.IdeaConverter;
import com.cms.entity.Category;
import com.cms.entity.Document;
import com.cms.entity.Idea;
import com.cms.entity.User;
import org.hibernate.engine.jdbc.StreamUtils;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class IdeaServiceImp implements IdeaService {
    @Autowired
    IdeaRepository ideaRepository;

    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    GoogleStorageInterface googleStorage;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    CategoryRepo categoryRepo;

    @Value("${file.upload-dir}")
    String uploadDir;

    @Value("${file.download-dir}")
    String downloadDir;

    @Override
    public PaginationT<ListIdeaRes> findIdea(Long depaId, Integer page, Integer size) {
        PaginationT<ListIdeaRes> list = new PaginationT<>();
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<IdeaConverter> data = ideaRepository.findByCategoryId(depaId, pageable);
        list.setItems(data.stream().map(converter -> {
            ListIdeaRes res = new ListIdeaRes();
            if (converter == null) return null;

            res.setDepartmentId(converter.getCategory());
            res.setDescription(converter.getDescription());
            res.setCommentList(converter.getDetailComment());
            res.setLikesList(converter.getDetailLikes());
            res.setTimeUp(converter.getTimeUp());
            res.setTotalLike(converter.getTotalLike());
            res.setTotalComment(converter.getTotalComment());
            res.setStaffId(converter.getStaffId());
            res.setIdeaId(converter.getId());

            return res;
        }).collect(Collectors.toList()));
        list.setTotal(data.getTotalElements());
        return list;
    }

    public boolean checkClosureTime(String startDate, String endDate) {
        Instant time = Instant.now();
        if(time.compareTo(Instant.parse(startDate)) >= 0 && time.compareTo(Instant.parse(endDate))<= 0) return true;
        return false;
    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public UploadFileResDTO uploadDocumentInScheduled(UploadReq req){
        Optional<User> userOpt = userRepo.findById(req.getUserId());
        if(userOpt.isEmpty())
            throw new RuntimeException("Not Found");
        User user = userOpt.get();
        if(!user.getRole().equals(ERole.STAFF.getValue()))
            throw new RuntimeException(String.format("Author is not %s", ERole.STAFF));
        if(!checkClosureTime(req.getStartDate(), req.getEndDate()))
            throw new RuntimeException(String.format("Out of time to submit: %s", new RuntimeException().getLocalizedMessage()));
        String fileName = fileStorageService.storeFile(req.getFile());

        // build uri to download
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download-file/")
                .path(fileName)
                .toUriString();

        //build uri to contact
        String fileOriginalUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/")
                .path(fileName)
                .toUriString();
        Document document = new Document();
        document.setUrl(fileOriginalUri);
        document.setName(fileName);
        document.setCreatedDate(Instant.now());
        document.setUrlDownload(fileDownloadUri);
        document.setCategoryId(req.getCategoryId());
        document.setUserId(req.getUserId());
        documentRepo.save(document);

        //save Idea
        Idea idea = new Idea();
        idea.setDescription(req.getDescription());
        idea.setDocument(document);
        idea.setStartDate(Instant.parse(req.getStartDate()));
        idea.setTimeUp(Instant.parse(req.getEndDate()));
        idea.setCreatedDate(Instant.now());
        idea.setDepartmentId(req.getDepartmentId());
        Category category = new Category();
        category.setId(req.getCategoryId());
        idea.setCategory(category);
        ideaRepository.save(idea);

        UploadFileResDTO dto = new UploadFileResDTO(fileName, fileDownloadUri, fileOriginalUri, req.getFile().getContentType(), req.getFile().getSize());
        return dto;
    }

    @Override
    public void downloadFile(DownloadReq req) throws Exception{
        File file = ResourceUtils.getFile(uploadDir);
        StringBuilder builder = new StringBuilder(file.toString());
        String[] files = file.list();
        List<String> filesConvert = Arrays.asList(files);
        List<Document> documents = documentRepo.findByNameIn(filesConvert);

        List<String> listRes = documents.stream().map(Document::getName).collect(Collectors.toList());
        for(Document document : documents){
            if(document.getCreatedDate().compareTo(Instant.parse(req.getStartDate()))>=0
                    && document.getCreatedDate().compareTo(Instant.parse(req.getEndDate()))<=0)
                listRes.add(builder.append("\\").append(document.getName()).toString());
            System.err.println(document);
        }
        zipFile(listRes, ResourceUtils.getFile(downloadDir));
    }
    public static void zipFile(List<String> path, File zipPath) throws IOException {
        try {
            FileOutputStream sos = new FileOutputStream(zipPath);
            ZipOutputStream zos = new ZipOutputStream(sos)
            ;
            List<String> filesToBeZipped = path.stream().distinct().collect(Collectors.toList());

            FileInputStream fis;
            for (String filePath : filesToBeZipped) {
                File fileToBeZipped = new File(filePath);

                if (!fileToBeZipped.exists() || fileToBeZipped.isDirectory()) {
                    zos.close();
                    sos.close();
                    throw new FileNotFoundException("Could not fould file path");
                }
                fis = new FileInputStream(fileToBeZipped);

                ZipEntry zipEntry = new ZipEntry(fileToBeZipped.getName());
                long now = System.currentTimeMillis();
                zipEntry.setTime(now);

                zos.putNextEntry(zipEntry);

                byte[] bytes = new byte[8192];

                while (fis.read(bytes) >= 0) {
                    zos.write(bytes, 0, bytes.length);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            sos.close();
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}

