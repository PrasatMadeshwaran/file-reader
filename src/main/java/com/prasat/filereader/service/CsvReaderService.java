package com.prasat.filereader.service;

import com.prasat.filereader.model.dto.CsvDTO;
import com.prasat.filereader.model.entity.CsvEntity;
import com.prasat.filereader.response.FileReaderResponse;

import java.util.List;

public interface CsvReaderService {

    FileReaderResponse saveAll(List<CsvDTO> list);

    FileReaderResponse fetchAll();

    FileReaderResponse fetchByCode(String code) throws Exception;

    FileReaderResponse deleteAll();
}
