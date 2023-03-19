package com.prasat.filereader.controller;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.prasat.filereader.component.CsvFileComponent;
import com.prasat.filereader.model.dto.CsvDTO;
import com.prasat.filereader.response.FileReaderResponse;
import com.prasat.filereader.service.CsvReaderService;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.prasat.filereader.constant.FileReaderConstant.CSV_CONTENT_TYPE;

@Tag(name = "CSV File Reader")
@RestController
@RequestMapping("/csv")
@RequiredArgsConstructor
public class CsvFileReaderController {

    private final CsvReaderService csvReaderService;

    private final CsvFileComponent csvFileComponent;

    @PostMapping(path="/upload-data", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileReaderResponse uploadData(@RequestParam("csv-file") MultipartFile file) throws IOException {
        List<CsvDTO> csvDTOList = csvFileComponent.reader(file);
        return csvReaderService.saveAll(csvDTOList);
    }

    @GetMapping("/fetch-all-data")
    public FileReaderResponse fetchAllData() {
        return csvReaderService.fetchAll();
    }

    @GetMapping("/fetch-by-code/{code}")
    public FileReaderResponse fetchByCode(@PathVariable("code") String code) throws Exception {
        return csvReaderService.fetchByCode(code);
    }

    @DeleteMapping("/delete-all-data")
    public FileReaderResponse deleteAllData() {
        return csvReaderService.deleteAll();
    }
}
