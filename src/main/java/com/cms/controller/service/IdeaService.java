package com.cms.controller.service;

import com.cms.config.PaginationT;
import com.cms.config.dto.UploadFileResDTO;
import com.cms.controller.request.DownloadReq;
import com.cms.controller.request.UploadReq;
import com.cms.controller.response.IdeaDetailRes;
import com.cms.controller.response.ListIdeaRes;
import org.springframework.core.io.InputStreamResource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IdeaService {
    PaginationT<ListIdeaRes> findIdea(Long depaId, String sortBy, Integer page, Integer size) throws Exception;

    ListIdeaRes uploadDocumentInScheduled(UploadReq req) throws IOException;

    void downloadFile(DownloadReq req) throws Exception;

    InputStreamResource exportAllListIdeaInCsv(Long departmentId, String sortBy) throws Exception;

    IdeaDetailRes getDetailRes(Long ideaId, Integer page, Integer size);
}
