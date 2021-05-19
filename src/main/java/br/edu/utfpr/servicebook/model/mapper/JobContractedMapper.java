package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.JobContractedDTO;
import br.edu.utfpr.servicebook.model.entity.JobContracted;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobContractedMapper {
    @Autowired
    private ModelMapper mapper;

    public JobContractedDTO toDto(JobContracted entity){
        JobContractedDTO dto = mapper.map(entity, JobContractedDTO.class);
        return dto;
    }

    public JobContractedDTO toResponseDto(JobContracted entity) {
        JobContractedDTO dto = mapper.map(entity, JobContractedDTO.class);
        dto.setId(dto.getId());
        dto.setComments(dto.getComments());
        dto.setStatus(dto.getStatus());
        dto.setRating(dto.getRating());
        return dto;
    }

    public JobContracted toEntity(JobContractedDTO dto) {
        JobContracted entity = mapper.map(dto, JobContracted.class);
        return entity;
    }
}