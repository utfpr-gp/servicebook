package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.ProfessionalServiceOffering;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfessionalServiceOfferingMapper {

    @Autowired
    private ModelMapper mapper;

    public ProfessionalServiceOfferingDTO toDTO(ProfessionalServiceOffering entity){
        ProfessionalServiceOfferingDTO dto = mapper.map(entity, ProfessionalServiceOfferingDTO.class);
        return dto;
    }

    public ProfessionalServiceOffering toEntity(ProfessionalServiceOfferingDTO dto){
        ProfessionalServiceOffering entity = mapper.map(dto, ProfessionalServiceOffering.class);
        return entity;
    }

    public ProfessionalServiceOfferingDTO toSearchItemDto(ProfessionalServiceOffering entity){
        ProfessionalServiceOfferingDTO dto = mapper.map(entity, ProfessionalServiceOfferingDTO.class);
        return dto;
    }
}
