package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.ProfessionalDTO;
import br.edu.utfpr.servicebook.model.dto.ProfessionalMinDTO;
import br.edu.utfpr.servicebook.model.dto.ProfessionalSearchItemDTO;
import br.edu.utfpr.servicebook.model.entity.Professional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public ProfessionalSearchItemDTO toSearchItemDto(Professional entity, List<ExpertiseDTO> expertises){
        ProfessionalSearchItemDTO dto = mapper.map(entity, ProfessionalSearchItemDTO.class);
        dto.setExpertises(expertises);
        return dto;
    }

    public ProfessionalDTO toResponseDto(Professional entity) {
        ProfessionalDTO dto = mapper.map(entity, ProfessionalDTO.class);
        dto.setId(dto.getId());
        dto.setCpf(dto.getCpf());
        dto.setDescription(dto.getDescription());
        dto.setRating(dto.getRating());
        dto.setDenounceAmount(dto.getDenounceAmount());
        return dto;
    }

    public Professional toEntity(ProfessionalDTO dto) {
        Professional entity = mapper.map(dto, Professional.class);
        return entity;
    }
}