package br.edu.utfpr.servicebook.model.mapper;


import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.util.sidePanel.UserTemplateInfo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class IndividualMapper {

    @Autowired
    private ModelMapper mapper;

    public IndividualDTO toDto(Individual entity) {
        IndividualDTO dto = mapper.map(entity, IndividualDTO.class);
        return dto;
    }

    public IndividualDTO toDto(Optional entity) {
        IndividualDTO dto = mapper.map(entity, IndividualDTO.class);
        return dto;
    }

    public UserTemplateInfo toDtoside(Individual entity) {
        UserTemplateInfo dto = mapper.map(entity, UserTemplateInfo.class);
//        dto.setExpertise(mapper.map(entity1, ExpertiseDTO.class));
        return dto;
    }

    public IndividualMinDTO toMinDto(Individual entity){
        IndividualMinDTO dto = mapper.map(entity, IndividualMinDTO.class);
        return dto;
    }

    public IndividualDTO toResponseDto(Individual entity) {
        IndividualDTO dto = mapper.map(entity, IndividualDTO.class);
        dto.setId(dto.getId());
        dto.setCpf(dto.getCpf());
        dto.setDescription(dto.getDescription());
        dto.setRating(dto.getRating());
        dto.setDenounceAmount(dto.getDenounceAmount());
        dto.setProfile(dto.getProfile());
        return dto;
    }

    public Individual toEntity(IndividualDTO dto) {
        Individual entity = mapper.map(dto, Individual.class);
        return entity;
    }

    public Individual optionalToEntity(Optional dto){
        Individual entity = mapper.map(dto, Individual.class);
        return entity;
    }

    public ProfessionalSearchItemDTO toSearchItemDto(Individual entity, List<ExpertiseDTO> expertises){
        ProfessionalSearchItemDTO dto = mapper.map(entity, ProfessionalSearchItemDTO.class);
        dto.setExpertises(expertises);
        return dto;
    }

}
