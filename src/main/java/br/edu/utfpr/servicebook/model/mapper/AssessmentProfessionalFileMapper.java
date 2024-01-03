package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.AssessmentProfessionalFileDTO;
import br.edu.utfpr.servicebook.model.dto.ServiceDTO;
import br.edu.utfpr.servicebook.model.entity.AssessmentProfessionalFiles;
import br.edu.utfpr.servicebook.model.entity.Service;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssessmentProfessionalFileMapper {
    @Autowired
    private ModelMapper mapper;

    public AssessmentProfessionalFileDTO toDto(AssessmentProfessionalFiles entity){
        AssessmentProfessionalFileDTO dto = mapper.map(entity, AssessmentProfessionalFileDTO.class);
        return dto;
    }

    public AssessmentProfessionalFiles toEntity(AssessmentProfessionalFileDTO dto){
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        AssessmentProfessionalFiles entity = mapper.map(dto, AssessmentProfessionalFiles.class);
        return entity;
    }

}
