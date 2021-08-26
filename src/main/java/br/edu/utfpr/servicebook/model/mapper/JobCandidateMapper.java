package br.edu.utfpr.servicebook.model.mapper;


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

    public JobCandidateMinDTO toMinDto(JobCandidate entity, Optional<Long> totalCandidates) {
        JobCandidateMinDTO dto = mapper.map(entity, JobCandidateMinDTO.class);
        dto.getJobRequest().setDateCreated(this.dateFormat.format(entity.getJobRequest().getDateCreated()));
        dto.getJobRequest().setDateExpired(this.dateFormat.format(entity.getJobRequest().getDateExpired()));
        dto.setDate(this.dateFormat.format(entity.getDate()));
        dto.getJobRequest().setTotalCandidates(totalCandidates.get());
        dto.getJobRequest().setTextualDate(DateUtil.getTextualDate(DateUtil.toLocalDate(entity.getJobRequest().getDateExpired())));

        return dto;
    }

}
