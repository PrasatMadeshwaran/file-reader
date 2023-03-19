package com.prasat.filereader.component;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.prasat.filereader.exception.FileTypeNotSupportedException;
import com.prasat.filereader.model.dto.CsvDTO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import static com.prasat.filereader.constant.FileReaderConstant.CSV_CONTENT_TYPE;

@Component
public class CsvFileComponent {


    public List<CsvDTO> reader(MultipartFile file) throws IOException {
        if(!CSV_CONTENT_TYPE.equals(file.getContentType())) {
            throw new FileTypeNotSupportedException(file.getContentType(),"csv");
        }

        Reader reader = new InputStreamReader(file.getInputStream());

        CsvToBean<CsvDTO> csvToBean = new CsvToBeanBuilder(reader)
                .withSkipLines(1)
                .withType(CsvDTO.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        return csvToBean.parse();
    }

}
