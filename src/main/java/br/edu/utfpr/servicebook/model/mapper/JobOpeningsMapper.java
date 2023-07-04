package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.CategoryDTO;
import br.edu.utfpr.servicebook.model.dto.JobOpeningsDTO;
import br.edu.utfpr.servicebook.model.entity.JobOpenings;
import jdk.jfr.Category;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobOpeningsMapper {
    @Autowired
    private ModelMapper mapper;

    public JobOpeningsDTO toDto(JobOpenings entity){
        JobOpeningsDTO dto = mapper.map(entity, JobOpeningsDTO.class);
        dto.setExpertiseId(entity.getExpertise().getId());
        return dto;
    }

    public JobOpenings toEntity(JobOpeningsDTO dto){
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        JobOpenings entity = mapper.map(dto, JobOpenings.class);

        return entity;
    }
}
