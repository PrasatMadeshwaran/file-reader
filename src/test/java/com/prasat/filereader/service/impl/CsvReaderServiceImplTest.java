package com.prasat.filereader.service.impl;


import com.prasat.filereader.exception.DataNotFoundException;
import com.prasat.filereader.model.dto.CsvDTO;
import com.prasat.filereader.model.entity.CsvEntity;
import com.prasat.filereader.model.mapper.CsvMapper;
import com.prasat.filereader.repository.CsvRepository;
import com.prasat.filereader.response.FileReaderResponse;
import com.prasat.filereader.service.CsvReaderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvReaderServiceImplTest {

    @Mock
    private CsvRepository mockCsvRepository;

    private CsvReaderService mockCsvService;

    @BeforeEach
    void setUp() {
        mockCsvService = new CsvReaderServiceImpl(mockCsvRepository);
    }

    @Test
    void shouldReturn200_andSaveSuccessfully_whenSaveAllIsCalled() {
        List<CsvDTO> csvDTOList = new ArrayList<>();
        csvDTOList.add(new CsvDTO("ZIB", "ZIB001", "271636001", "Polsslag regelmatig", "The long description is necessary", new Date(), null, 1));
        csvDTOList.add(new CsvDTO("ZIB", "ZIB001", "61086009", "Polsslag onregelmatig", null, new Date(), null, 2));
        csvDTOList.add(new CsvDTO("ZIB", "ZIB001", "Type 1", "Losse harde keutels, zoals noten", null, new Date(), null, null));
        List<CsvEntity> csvEntityList = CsvMapper.CSV_MAPPER.toCsvEntityList(csvDTOList);
        FileReaderResponse fileReaderResponse = new FileReaderResponse();
        fileReaderResponse.setStatus(HttpStatus.OK.value());
        fileReaderResponse.setResourceData(csvEntityList);
        when(mockCsvRepository.saveAll(any())).thenReturn(csvEntityList);
        mockCsvService.saveAll(csvDTOList);
        verify(mockCsvRepository).saveAll(any());
    }

    @Test
    void shouldReturn200_withData_whenDataExist_whenFetchAllDataIsCalled() {
        List<CsvDTO> expectedCsvDTOList = CsvMapper.CSV_MAPPER.toCsvModelList(getCSVEntityList());
        when(mockCsvRepository.findAll()).thenReturn(getCSVEntityList());
        FileReaderResponse readerResponse= mockCsvService.fetchAll();
        List<CsvDTO> actualCsvDTOList = (List<CsvDTO>) readerResponse.getResourceData();
        assertEquals(expectedCsvDTOList.size(), actualCsvDTOList.size());
    }

    @Test
    void shouldReturn404_throwNoFoundException_whenNoDataExist_whenFetchAllDataIsCalled() {
        when(mockCsvRepository.findAll()).thenReturn(new ArrayList<>());
        Assertions.assertThrows(DataNotFoundException.class, () -> mockCsvService.fetchAll());
    }

    @Test
    void shouldReturn200_withData_forGivenCode_whenFetchByCodeIsCalled() throws Exception {
        String code = "271636001";
        CsvEntity expectedCsvEntity = new CsvEntity(1L, "ZIB", "ZIB001", code, "Polsslag regelmatig", "The long description is necessary", new Date(), null, 1);
        when(mockCsvRepository.findByCode(code)).thenReturn(Optional.of(expectedCsvEntity));
        FileReaderResponse readerResponse = mockCsvService.fetchByCode(code);
        CsvDTO actualCsvDTO = (CsvDTO) readerResponse.getResourceData();
        assertEquals(expectedCsvEntity.getCode(), actualCsvDTO.getCode());
    }

    @Test
    void shouldReturn404_withThrowNoFoundException_whenNoCodeExists_whenFetchByCodeIsCalled() {
        String code = "271636001";
        when(mockCsvRepository.findByCode(code)).thenReturn(Optional.empty());
        Assertions.assertThrows(DataNotFoundException.class, () -> mockCsvService.fetchByCode(code));
    }

    @Test
    void shouldReturn200_whenDataExists_whenDeleteAllIsCalled() {
        when(mockCsvRepository.findAll()).thenReturn(getCSVEntityList());
        mockCsvService.deleteAll();
        verify(mockCsvRepository).deleteAll();
    }

    @Test
    void shouldReturn404_withThrowNoFoundException_whenNoDataExists_whenDeleteAllIsCalled() {
        when(mockCsvRepository.findAll()).thenReturn(new ArrayList<>());
        Assertions.assertThrows(DataNotFoundException.class, () -> mockCsvService.deleteAll());
    }

    private List<CsvEntity> getCSVEntityList() {
        List<CsvEntity> csvEntityList = new ArrayList<>();
        csvEntityList.add(new CsvEntity(1L, "ZIB", "ZIB001", "271636001", "Polsslag regelmatig", "The long description is necessary", new Date(), null, 1));
        csvEntityList.add(new CsvEntity(2L, "ZIB", "ZIB001", "61086009", "Polsslag onregelmatig", null, new Date(), null, 2));
        csvEntityList.add(new CsvEntity(3L,"ZIB", "ZIB001", "Type 1", "Losse harde keutels, zoals noten", null, new Date(), null, null));
        return csvEntityList;
    }

    private FileReaderResponse getFileReaderResponse() {
        FileReaderResponse fileReaderResponse = new FileReaderResponse();
        fileReaderResponse.setStatus(HttpStatus.OK.value());
        fileReaderResponse.setResourceData(getCSVEntityList());
        return fileReaderResponse;
    }
}