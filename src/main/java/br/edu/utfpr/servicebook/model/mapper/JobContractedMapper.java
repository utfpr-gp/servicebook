package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.JobContractedDTO;
import br.edu.utfpr.servicebook.model.dto.JobContractedFullDTO;
import br.edu.utfpr.servicebook.model.entity.JobContracted;
import br.edu.utfpr.servicebook.util.DateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JobContractedMapper {
    @Autowired
    private ModelMapper mapper;

    public JobContractedDTO toDto(JobContracted entity) {
        JobContractedDTO dto = mapper.map(entity, JobContractedDTO.class);
        return dto;
    }

    public JobContractedDTO toResponseDto(JobContracted entity) {
        JobContractedDTO dto = mapper.map(entity, JobContractedDTO.class);
        dto.setId(entity.getId());
        dto.setComments(entity.getComments());
        dto.setTodoDate(entity.getTodoDate());
        dto.setHiredDate(entity.getHiredDate());
        dto.setStatus(entity.getJobRequest().getStatus().toString());
        dto.setRating(entity.getRating());
        return dto;
    }

    public JobContracted toEntity(JobContractedDTO dto) {
        JobContracted entity = mapper.map(dto, JobContracted.class);
        return entity;
    }

    public JobContractedFullDTO toFullDto(JobContracted entity, Optional<Long> totalCandidates) {
        JobContractedFullDTO dto = mapper.map(entity, JobContractedFullDTO.class);
        dto.getJobRequest().setTotalCandidates(totalCandidates.get());
        dto.getJobRequest().setTextualDate(DateUtil.getTextualDate((entity.getJobRequest().getDateTarget())));

        return dto;
    }

}
