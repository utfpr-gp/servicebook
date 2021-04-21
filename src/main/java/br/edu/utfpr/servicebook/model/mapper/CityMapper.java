package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.CityDTO;
import br.edu.utfpr.servicebook.model.entity.City;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {
    private ModelMapper mapper;

    public CityDTO toDto(City entity){
        CityDTO dto = mapper.map(entity, CityDTO.class);
        return dto;
    }

    public City toEntity(CityDTO dto) {
        City entity = mapper.map(dto, City.class);
        return entity;
    }

}
