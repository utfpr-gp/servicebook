package br.edu.utfpr.servicebook.model.mapper;


import br.edu.utfpr.servicebook.model.dto.JobCandidateDTO;
import br.edu.utfpr.servicebook.model.dto.JobCandidateMinDTO;
import br.edu.utfpr.servicebook.model.entity.JobCandidate;
import br.edu.utfpr.servicebook.util.DateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Optional;

@Component
public class JobCandidateMapper {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private ModelMapper mapper;

    public JobCandidateDTO toDto(JobCandidate entity){
        JobCandidateDTO dto = mapper.map(entity, JobCandidateDTO.class);
        return dto;
    }

    public JobCandidate toEntity(JobCandidateDTO dto){
        JobCandidate entity = mapper.map(dto, JobCandidate.class);
        return entity;
    }
    public JobCandidateMinDTO toMinDto(JobCandidate entity, Optional<Long> totalCandidates) {
        JobCandidateMinDTO dto = mapper.map(entity, JobCandidateMinDTO.class);
        dto.getJobRequest().setDateCreated(this.dateFormat.format(entity.getJobRequest().getDateCreated()));
        dto.getJobRequest().setDateExpired(this.dateFormat.format(entity.getJobRequest().getDateExpired()));
        dto.setDate(this.dateFormat.format(entity.getDateCreated()));
        dto.getJobRequest().setTotalCandidates(totalCandidates.get());
        dto.getJobRequest().setTextualDate(DateUtil.getTextualDate(DateUtil.toLocalDate(entity.getJobRequest().getDateExpired())));

        return dto;
    }

}