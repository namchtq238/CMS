package com.cms.service;

import com.cms.config.excel.CsvConfig;
import com.cms.controller.response.ListIdeaRes;
import com.cms.controller.service.ExportService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class ExportServiceImp implements ExportService {
    @Override
    public ByteArrayInputStream ideasToCsv(List<ListIdeaRes> list) {
        ByteArrayInputStream in = CsvConfig.ideasToCSV(list);
        return in;
    }
}
