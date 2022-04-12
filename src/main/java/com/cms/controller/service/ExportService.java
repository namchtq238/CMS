package com.cms.controller.service;

import com.cms.controller.response.ListIdeaRes;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ExportService {
    ByteArrayInputStream ideasToCsv(List<ListIdeaRes> list);
}
