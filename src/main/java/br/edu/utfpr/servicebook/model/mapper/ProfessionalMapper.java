package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.ProfessionalDTO;
import br.edu.utfpr.servicebook.model.dto.ProfessionalMinDTO;
import br.edu.utfpr.servicebook.model.entity.Individual;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfessionalMapper {
    @Autowired
    private ModelMapper mapper;

    public ProfessionalDTO toDto(Individual entity){
        ProfessionalDTO dto = mapper.map(entity, ProfessionalDTO.class);
        return dto;
    }

    public ProfessionalMinDTO toMinDto(Individual entity){
        ProfessionalMinDTO dto = mapper.map(entity, ProfessionalMinDTO.class);
        return dto;
    }

    public ProfessionalDTO toResponseDto(Individual entity) {
        ProfessionalDTO dto = mapper.map(entity, ProfessionalDTO.class);
        dto.setId(dto.getId());
        dto.setCpf(dto.getCpf());
        dto.setDescription(dto.getDescription());
        dto.setRating(dto.getRating());
        dto.setDenounceAmount(dto.getDenounceAmount());
        return dto;
    }

    public Individual toEntity(ProfessionalDTO dto) {
        Individual entity = mapper.map(dto, Individual.class);
        return entity;
    }
}