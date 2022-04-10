package com.cms.controller.service;

import com.cms.config.PaginationT;
import com.cms.controller.request.UploadReq;
import com.cms.controller.response.ListIdeaRes;
import com.cms.entity.Document;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

public interface IdeaService {
    PaginationT<ListIdeaRes> findIdea(Integer page, Integer size);

    boolean checkClosureTime(Instant startDate, Instant endDate);

    void uploadDocumentInScheduled(UploadReq req) throws IOException;
}
