package com.cms.service;

import com.cms.config.PaginationT;
import com.cms.config.dto.MailDTO;
import com.cms.config.dto.UploadFileResDTO;
import com.cms.config.storage.FileStorageService;
import com.cms.config.storage.GoogleStorageInterface;
import com.cms.constants.ERole;
import com.cms.controller.request.DownloadReq;
import com.cms.controller.request.UploadReq;
import com.cms.controller.response.IdeaDetailRes;
import com.cms.controller.response.ListIdeaRes;
import com.cms.controller.service.ExportService;
import com.cms.controller.service.IdeaService;
import com.cms.database.*;
import com.cms.database.converter.IdeaConverter;
import com.cms.entity.*;
import com.cms.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Paths;
import java.time.Instant;
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
    ExportService export;

    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    GoogleStorageInterface googleStorage;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    MailSenderCustom mailSender;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    Mapper mapper;

    @Autowired
    QaRepo qaRepo;

    @Autowired
    LikeRepo likeRepo;

    @Value("${file.upload-dir}")
    String uploadDir;

    @Value("${file.download-dir}")
    String downloadDir;

    //nhớ đánh index
    @Override
    public PaginationT<ListIdeaRes> findIdea(Long depaId, String sortBy, Integer page, Integer size) throws Exception{
        PaginationT<ListIdeaRes> list = new PaginationT<>();

        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<IdeaConverter> data = ideaRepository.findByCategoryId(depaId, pageable);
        List<ListIdeaRes> listIdeaRes = data.stream().map(converter -> {
            ListIdeaRes res = new ListIdeaRes();
            if (converter == null) return null;

            res.setDepartmentId(converter.getDepartmentId());
            res.setDescription(converter.getDescription());
            res.setCategoryId(converter.getCategoryId());
            res.setTimeUp(converter.getTimeUp());
            res.setTotalLike(likeRepo.countLikesByIdeaId(converter.getId()));
            res.setTotalComment(commentRepo.countCommentForDetailIdea(converter.getId()));
            res.setStaffId(converter.getStaffId());
            res.setIdeaId(converter.getId());
            res.setName(converter.getIdeaName());

            return res;
        }).collect(Collectors.toList());
        List<ListIdeaRes> response;
        switch (sortBy){
            case "LIKE":
                response = listIdeaRes.stream().sorted((a,b) -> b.getTotalLike().compareTo(a.getTotalLike())).collect(Collectors.toList());
                break;
            case "COMMENT":
                response = listIdeaRes.stream().sorted((a,b) -> b.getTotalComment().compareTo(a.getTotalComment())).collect(Collectors.toList());
                break;
            default:
                response = listIdeaRes;
        }

        list.setItems(response);
        list.setTotal(data.getTotalElements());
        return list;
    }

    public boolean checkClosureTime(String startDate, String endDate) {
        Instant time = Instant.now();
        if(time.compareTo(Instant.parse(startDate)) >= 0 && time.compareTo(Instant.parse(endDate))<= 0) return true;
        return false;
    }


    //Không đánh index vì -> giảm hiệu năng save
    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public ListIdeaRes uploadDocumentInScheduled(UploadReq req){
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
        String fileDownloadUri = Paths.get(uploadDir).toString() + "\\" + fileName;

        //build uri to contact
        String fileOriginalUri = Paths.get(uploadDir).toString() + "\\" + fileName;
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
        idea.setName(req.getName());
        idea.setStartDate(Instant.parse(req.getStartDate()));
        idea.setTimeUp(Instant.parse(req.getEndDate()));
        idea.setCreatedDate(Instant.now());
        idea.setDepartmentId(req.getDepartmentId());
        Category category = new Category();
        category.setId(req.getCategoryId());
        idea.setCategory(category);
        ideaRepository.save(idea);

        //send mail
        MailDTO mailDTO = new MailDTO();
        QA qa = qaRepo.getByDepartmentsId(req.getDepartmentId());
        mailDTO.setContent("Someone has name " + user.getName()  + "post an idea to your department");
        mailDTO.setFrom("noreply@gmail.com");
        mailDTO.setTo(qa.getUser().getEmail());
        mailDTO.setSubject("User Post idea");
        mailSender.sendMail(mailDTO);

        ListIdeaRes res = mapper.ideaToRes(idea);
        res.setTotalComment(0);
        res.setTotalLike(0);
        res.setUrl(fileOriginalUri);


        return res;
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

    @Override
    public InputStreamResource exportAllListIdeaInCsv(Long departmentId, String sortBy) throws Exception{
        List<ListIdeaRes> listRes = findIdea(departmentId, sortBy, 0, 1000).getItems().stream().collect(Collectors.toList());
        InputStreamResource resource = new InputStreamResource(export.ideasToCsv(listRes));
        return resource;
    }

    @Override
    public IdeaDetailRes getDetailRes(Long ideaId, Integer page, Integer size) {
        Optional<Idea> ideaOpt = ideaRepository.findById(ideaId);
        Pageable pageable = PageRequest.of(page, size);
        if(ideaOpt.isEmpty()) return null;
        Idea idea = ideaOpt.get();
        Page<Comment> commentList = commentRepo.findByIdeaId(ideaId, pageable);
        Integer totalLike = likeRepo.countLikesByIdeaId(ideaId);
        //sap xep theo ngay cmt moi nhat
        Integer totalComment = commentRepo.countCommentForDetailIdea(ideaId);
        List<Comment> commentContents = commentList.stream().sorted((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate())).collect(Collectors.toList());

        PaginationT<String> listCommentContent = new PaginationT<>();
        listCommentContent.setTotal(commentList.getTotalElements());
        listCommentContent.setItems(commentContents.stream().map(Comment::getContent).collect(Collectors.toList()));

        IdeaDetailRes res = new IdeaDetailRes();

        res.setIdeaId(idea.getId());
        res.setDetailComment(listCommentContent);
        res.setIdeaName(idea.getName());
        res.setDescription(idea.getDescription());
        res.setTotalLike(totalLike);
        res.setTotalComment(totalComment);

        return res;
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

