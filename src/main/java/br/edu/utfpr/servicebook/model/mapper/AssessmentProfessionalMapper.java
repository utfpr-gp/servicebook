package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.AssessmentProfessional;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssessmentProfessionalMapper {
    @Autowired
    private ModelMapper mapper;

    public AssessmentProfessionalDTO toDTO(AssessmentProfessional entity) {
        return mapper.map(entity, AssessmentProfessionalDTO.class);
    }

    public AssessmentProfessionalDTO toResponseDTO(AssessmentProfessional entity) {
        AssessmentProfessionalDTO dto = mapper.map(entity, AssessmentProfessionalDTO.class);
        dto.setId(entity.getId());
        return dto;
    }

    public AssessmentProfessional toEntity(AssessmentProfessionalDTO dto) {
        return mapper.map(dto, AssessmentProfessional.class);
    }
}
