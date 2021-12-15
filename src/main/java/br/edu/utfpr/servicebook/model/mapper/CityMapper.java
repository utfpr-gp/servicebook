package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.CityDTO;
import br.edu.utfpr.servicebook.model.dto.CityDTO2;
import br.edu.utfpr.servicebook.model.dto.CityMidDTO;
import br.edu.utfpr.servicebook.model.dto.CityMinDTO;
import br.edu.utfpr.servicebook.model.entity.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {
    @Autowired
    private ModelMapper mapper;

    public CityDTO toDto(City entity) {
        CityDTO dto = mapper.map(entity, CityDTO.class);
        return dto;
    }

    public CityDTO toResponseDto(City entity) {
        CityDTO dto = mapper.map(entity, CityDTO.class);
        dto.setName(entity.getName());
        return dto;
    }

    public City toEntity(CityDTO dto) {
        City entity = mapper.map(dto, City.class);
        return entity;
    }

    public CityDTO2 toResponseDetail(City entity) {
        CityDTO2 dto = mapper.map(entity, CityDTO2.class);
        dto.setName(entity.getName());
        return dto;
    }

    public CityMidDTO toMidDto(City entity) {
        CityMidDTO dto = mapper.map(entity, CityMidDTO.class);
        return dto;
    }

    public CityMinDTO toMinDto(City entity) {
        CityMinDTO dto = mapper.map(entity, CityMinDTO.class);
        return dto;
    }

}
