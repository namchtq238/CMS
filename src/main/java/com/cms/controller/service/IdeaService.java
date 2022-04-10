package com.cms.controller.service;

import com.cms.config.PaginationT;
import com.cms.controller.response.ListIdeaRes;

import java.io.File;
import java.io.IOException;

public interface IdeaService {
    PaginationT<ListIdeaRes> findIdea(Integer page, Integer size);

    void uploadDocumentToGoogleCloud(File file) throws IOException;

    void uploadDocumentInScheduled(Long id, File file);
}
