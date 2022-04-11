package com.cms.controller.service;

import com.cms.config.PaginationT;
import com.cms.config.dto.UploadFileResDTO;
import com.cms.controller.request.DownloadReq;
import com.cms.controller.request.UploadReq;
import com.cms.controller.response.ListIdeaRes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IdeaService {
    PaginationT<ListIdeaRes> findIdea(Long depaId, Integer page, Integer size);

    UploadFileResDTO uploadDocumentInScheduled(UploadReq req) throws IOException;

    void downloadFile(DownloadReq req, HttpServletResponse response);
}
