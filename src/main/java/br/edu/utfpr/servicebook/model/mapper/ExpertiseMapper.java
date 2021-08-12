package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpertiseMapper {
    @Autowired
    private ModelMapper mapper;

    public ExpertiseDTO toDto(Expertise entity){
        ExpertiseDTO dto = mapper.map(entity, ExpertiseDTO.class);
        return dto;
    }

    public ExpertiseDTO toResponseDto(Expertise entity) {
        ExpertiseDTO dto = mapper.map(entity, ExpertiseDTO.class);
        dto.setId(dto.getId());
        return dto;
    }

    public Expertise toEntity(ExpertiseDTO dto) {
        Expertise entity = mapper.map(dto, Expertise.class);
        return entity;
    }
}