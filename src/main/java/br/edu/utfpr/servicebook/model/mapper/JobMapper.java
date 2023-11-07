package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.JobDTO;
import br.edu.utfpr.servicebook.model.entity.Job;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {
    @Autowired
    private ModelMapper mapper;

    public JobDTO toDto(Job entity){
        JobDTO dto = mapper.map(entity, JobDTO.class);
        dto.setExpertiseId(entity.getExpertise().getId());
        return dto;
    }

    public Job toEntity(JobDTO dto){
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Job entity = mapper.map(dto, Job.class);

        return entity;
    }
}
