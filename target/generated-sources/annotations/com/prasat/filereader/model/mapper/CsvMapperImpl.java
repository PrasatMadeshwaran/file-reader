package com.prasat.filereader.model.mapper;

import com.prasat.filereader.model.dto.CsvDTO;
import com.prasat.filereader.model.entity.CsvEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-19T17:08:56+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
public class CsvMapperImpl implements CsvMapper {

    @Override
    public CsvDTO toCsvModel(CsvEntity csvEntity) {
        if ( csvEntity == null ) {
            return null;
        }

        CsvDTO csvDTO = new CsvDTO();

        csvDTO.setSource( csvEntity.getSource() );
        csvDTO.setCodeListCode( csvEntity.getCodeListCode() );
        csvDTO.setCode( csvEntity.getCode() );
        csvDTO.setDisplayValue( csvEntity.getDisplayValue() );
        csvDTO.setLongDescription( csvEntity.getLongDescription() );
        csvDTO.setFromDate( csvEntity.getFromDate() );
        csvDTO.setToDate( csvEntity.getToDate() );
        csvDTO.setSortingPriority( csvEntity.getSortingPriority() );

        return csvDTO;
    }

    @Override
    public List<CsvDTO> toCsvModelList(List<CsvEntity> csvEntityList) {
        if ( csvEntityList == null ) {
            return null;
        }

        List<CsvDTO> list = new ArrayList<CsvDTO>( csvEntityList.size() );
        for ( CsvEntity csvEntity : csvEntityList ) {
            list.add( toCsvModel( csvEntity ) );
        }

        return list;
    }

    @Override
    public CsvEntity toCsvEntity(CsvDTO csvModel) {
        if ( csvModel == null ) {
            return null;
        }

        CsvEntity csvEntity = new CsvEntity();

        csvEntity.setSource( csvModel.getSource() );
        csvEntity.setCodeListCode( csvModel.getCodeListCode() );
        csvEntity.setCode( csvModel.getCode() );
        csvEntity.setDisplayValue( csvModel.getDisplayValue() );
        csvEntity.setLongDescription( csvModel.getLongDescription() );
        csvEntity.setFromDate( csvModel.getFromDate() );
        csvEntity.setToDate( csvModel.getToDate() );
        csvEntity.setSortingPriority( csvModel.getSortingPriority() );

        return csvEntity;
    }

    @Override
    public List<CsvEntity> toCsvEntityList(List<CsvDTO> csvModelList) {
        if ( csvModelList == null ) {
            return null;
        }

        List<CsvEntity> list = new ArrayList<CsvEntity>( csvModelList.size() );
        for ( CsvDTO csvDTO : csvModelList ) {
            list.add( toCsvEntity( csvDTO ) );
        }

        return list;
    }
}
