package br.edu.utfpr.servicebook.model.mapper;


import br.edu.utfpr.servicebook.model.dto.JobRequestDTO;
import br.edu.utfpr.servicebook.model.dto.JobRequestMinDTO;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobRequestMapper {
    @Autowired
    private ModelMapper mapper;

    public JobRequestDTO toDto(JobRequest entity){
        JobRequestDTO dto = mapper.map(entity, JobRequestDTO.class);
        return dto;
    }

    public JobRequest toEntity(JobRequestDTO dto) {
        //Fazer ignorar
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        JobRequest entity = mapper.map(dto, JobRequest.class);
        return entity;
    }

    public JobRequestMinDTO toMinDto(JobRequest entity){
        JobRequestMinDTO dto = mapper.map(entity, JobRequestMinDTO.class);
        return dto;
    }


}
