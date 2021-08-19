package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.ProfessionalDTO;
import br.edu.utfpr.servicebook.model.dto.ProfessionalMinDTO;
import br.edu.utfpr.servicebook.model.entity.Professional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfessionalMapper {
    @Autowired
    private ModelMapper mapper;

    public ProfessionalDTO toDto(Professional entity){
        ProfessionalDTO dto = mapper.map(entity, ProfessionalDTO.class);
        return dto;
    }

    public ProfessionalMinDTO toMinDto(Professional entity){
        ProfessionalMinDTO dto = mapper.map(entity, ProfessionalMinDTO.class);
        return dto;
    }

    public ProfessionalDTO toResponseDto(Professional entity) {
        ProfessionalDTO dto = mapper.map(entity, ProfessionalDTO.class);
        return dto;
    }

    public Professional toEntity(ProfessionalDTO dto) {
        Professional entity = mapper.map(dto, Professional.class);
        return entity;
    }
}