package com.cms.controller.service;

import com.cms.config.PaginationT;
import com.cms.config.dto.UploadFileResDTO;
import com.cms.controller.request.UploadReq;
import com.cms.controller.response.ListIdeaRes;
import com.cms.entity.Document;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

public interface IdeaService {
    PaginationT<ListIdeaRes> findIdea(Long depaId, Integer page, Integer size);

    UploadFileResDTO uploadDocumentInScheduled(UploadReq req) throws IOException;
}
