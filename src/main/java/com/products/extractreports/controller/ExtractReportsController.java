package com.products.extractreports.controller;

import com.products.extractreports.model.ReportOutput;
import com.products.extractreports.utils.FileUtils;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * REST APIs for taking input text file with customer/product details and
 * generate json/csv.
 */
@RestController
public class ExtractReportsController {

    /*@RequestMapping("/extract/json")
    public List<ReportOutput> extractJSONReport(File inputFile) {
        return new ArrayList<>();
    }*/

    @RequestMapping(value = "/extract/json", method = RequestMethod.GET)
    public List<ReportOutput> extractJSONReport() throws Exception {
        return FileUtils.getLines();
    }

    @RequestMapping(value = "/extract/csv", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<?> extractCSVReport() throws Exception {
        ByteArrayOutputStream writer = FileUtils.writeCSV();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Output.csv")
                .body(writer.toByteArray());
    }

}
