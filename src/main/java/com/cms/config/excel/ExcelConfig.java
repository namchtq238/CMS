package com.cms.config.excel;

import com.cms.controller.response.ListIdeaRes;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Configuration
public class ExcelConfig {
    @Bean
    public static ByteArrayInputStream tutorialsToCSV(List<ListIdeaRes> ideas) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (ListIdeaRes idea : ideas) {
                List<String> data = Arrays.asList(
                        String.valueOf(idea.getIdeaId()),
                        idea.getCommentList(),
                        idea.getDescription()
                );
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
