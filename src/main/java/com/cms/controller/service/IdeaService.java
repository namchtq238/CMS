package com.cms.controller.service;

import com.cms.config.PaginationT;
import com.cms.config.dto.UploadFileResDTO;
import com.cms.controller.request.DownloadReq;
import com.cms.controller.request.UploadReq;
import com.cms.controller.response.IdeaDetailRes;
import com.cms.controller.response.ListIdeaRes;
import org.springframework.core.io.InputStreamResource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

public interface IdeaService {
    PaginationT<ListIdeaRes> findIdea(Long depaId, String sortBy, Integer page, Integer size) throws Exception;

    ListIdeaRes uploadDocumentInScheduled(UploadReq req) throws IOException;

    ByteArrayInputStream downloadFile(DownloadReq req) throws Exception;

    InputStreamResource exportAllListIdeaInCsv(Long departmentId, String sortBy) throws Exception;

    IdeaDetailRes getDetailRes(Long ideaId, Long userId, Integer page, Integer size);
}
