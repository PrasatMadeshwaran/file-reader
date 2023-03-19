package com.prasat.filereader.model.mapper;

import com.prasat.filereader.model.dto.CsvDTO;
import com.prasat.filereader.model.entity.CsvEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CsvMapper {

    CsvMapper CSV_MAPPER = Mappers.getMapper(CsvMapper.class);

    CsvDTO toCsvModel(CsvEntity csvEntity);

    List<CsvDTO> toCsvModelList(List<CsvEntity> csvEntityList);

    CsvEntity toCsvEntity(CsvDTO csvModel);

    List<CsvEntity> toCsvEntityList(List<CsvDTO> csvModelList);
}