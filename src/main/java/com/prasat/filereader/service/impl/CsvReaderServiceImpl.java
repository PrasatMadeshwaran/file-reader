package com.prasat.filereader.service.impl;

import com.prasat.filereader.exception.DataNotFoundException;
import com.prasat.filereader.model.dto.CsvDTO;
import com.prasat.filereader.model.entity.CsvEntity;
import com.prasat.filereader.repository.CsvRepository;
import com.prasat.filereader.response.FileReaderResponse;
import com.prasat.filereader.service.CsvReaderService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.prasat.filereader.model.mapper.CsvMapper.CSV_MAPPER;

@Service
@RequiredArgsConstructor
public class CsvReaderServiceImpl implements CsvReaderService {


    @Autowired
    private final CsvRepository csvRepository;


    @Override
    @Transactional
    public FileReaderResponse saveAll(List<CsvDTO> csvDTOList) {
        List<CsvEntity> csvEntities = CSV_MAPPER.toCsvEntityList(csvDTOList);
        List<CsvEntity> savedCsvEntities = csvRepository.saveAll(csvEntities);
        FileReaderResponse readerResponse = new FileReaderResponse();
        readerResponse.setStatus(HttpStatus.OK.value());
        readerResponse.setResourceData("Count : "+savedCsvEntities.size());
        readerResponse.setResponseMessage("File got uploaded successfully");
        return readerResponse;
    }

    @Override
    public FileReaderResponse fetchAll() {
        List<CsvEntity> csvEntities = csvRepository.findAll();
        if (csvEntities.isEmpty()) {
            throw new DataNotFoundException("No data found in CSV table");
        }
        FileReaderResponse readerResponse = new FileReaderResponse();
        readerResponse.setStatus(HttpStatus.OK.value());
        readerResponse.setResourceData(CSV_MAPPER.toCsvModelList(csvEntities));
        readerResponse.setResponseMessage("All data from table fetched successfully");
        return readerResponse;
    }

    @Override
    public FileReaderResponse fetchByCode(String code) {
        CsvEntity csvEntity = csvRepository.findByCode(code).orElseThrow(() -> new DataNotFoundException(code + " is not found in table"));
        FileReaderResponse readerResponse = new FileReaderResponse();
        readerResponse.setStatus(HttpStatus.OK.value());
        readerResponse.setResourceData(CSV_MAPPER.toCsvModel(csvEntity));
        readerResponse.setResponseMessage("Data from table fetched successfully");
        return readerResponse;
    }

    @Override
    @Transactional
    public FileReaderResponse deleteAll() {
        List<CsvEntity> csvEntities = csvRepository.findAll();
        if (csvEntities.isEmpty()) {
            throw new DataNotFoundException("No data found in CSV table");
        }
        csvRepository.deleteAll();
        FileReaderResponse readerResponse = new FileReaderResponse();
        readerResponse.setStatus(HttpStatus.OK.value());
        readerResponse.setResponseMessage("All Data from table deleted successfully");
        return readerResponse;
    }
}
