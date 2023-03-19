package com.prasat.filereader.controller;


import com.prasat.filereader.component.CsvFileComponent;
import com.prasat.filereader.model.dto.CsvDTO;
import com.prasat.filereader.response.FileReaderResponse;
import com.prasat.filereader.service.CsvReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CsvFileReaderControllerTest {

    @Mock
    private CsvReaderService csvReaderService;

    @Mock
    private CsvFileComponent csvFileComponent;

    @InjectMocks
    private CsvFileReaderController csvFileReaderController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        csvFileReaderController = new CsvFileReaderController(csvReaderService, csvFileComponent);
        mockMvc = MockMvcBuilders.standaloneSetup(csvFileReaderController).build();
    }

    @Test
    void shouldReturn200_whenUploadData_andCsvFileFormat() throws Exception {
        InputStream is = csvFileReaderController.getClass().getClassLoader().getResourceAsStream("exercise.csv");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("csv-file", "exercise.csv", "multipart/form-data", is);
        when(csvFileComponent.reader(any())).thenReturn(getCSVDataList());
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/csv/upload-data")
                        .file(mockMultipartFile))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn200_whenFetchAllData() throws Exception {
        when(csvReaderService.fetchAll()).thenReturn(getFileReaderResponse());
        mockMvc.perform(MockMvcRequestBuilders.get("/csv/fetch-all-data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn200_whenFetchDataByCode() throws Exception {
        FileReaderResponse fileReaderResponse = new FileReaderResponse();
        CsvDTO csvDTO = new CsvDTO("ZIB", "ZIB001", "61086009", "Polsslag onregelmatig", null, new Date(), null, 2);
        fileReaderResponse.setStatus(HttpStatus.OK.value());
        fileReaderResponse.setResourceData(csvDTO);
        when(csvReaderService.fetchByCode(any())).thenReturn(fileReaderResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/csv/fetch-by-code/61086009")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn200_withSuccessMessage_whenDeleteData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/csv/delete-all-data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private List<CsvDTO> getCSVDataList() {
        List<CsvDTO> csvDTOList = new ArrayList<>();
        CsvDTO csvDTO = new CsvDTO();
        csvDTO.setSource("ZIB");
        csvDTO.setCodeListCode("ZIB001");
        csvDTO.setCode("271636001");
        csvDTO.setDisplayValue("Polsslag regelmatig");
        csvDTO.setLongDescription("The long description is necessary");
        csvDTO.setFromDate(new Date());
        csvDTO.setToDate(null);
        csvDTO.setSortingPriority(1);
        csvDTOList.add(csvDTO);
        return csvDTOList;
    }

    private FileReaderResponse getFileReaderResponse() {
        FileReaderResponse fileReaderResponse = new FileReaderResponse();
        fileReaderResponse.setStatus(HttpStatus.OK.value());
        fileReaderResponse.setResourceData(getCSVDataList());
        return fileReaderResponse;
    }

}