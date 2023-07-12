package br.edu.utfpr.servicebook.model.mapper;


import br.edu.utfpr.servicebook.model.dto.JobCandidateDTO;
import br.edu.utfpr.servicebook.model.dto.JobCandidateMinDTO;
import br.edu.utfpr.servicebook.model.entity.JobCandidate;
import br.edu.utfpr.servicebook.util.DateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class JobCandidateMapper {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    private ModelMapper mapper;

    public JobCandidateDTO toDto(JobCandidate entity){
        JobCandidateDTO dto = mapper.map(entity, JobCandidateDTO.class);
        return dto;
    }

    /**
     * Método para converter uma entidade JobCandidate para um DTO com informações dos seguidores
     * @param entity
     * @param followingAmount
     * @return
     */
    public JobCandidateDTO toDTOWithFollowingInfo(JobCandidate entity, Long followingAmount){
        JobCandidateDTO dto = mapper.map(entity, JobCandidateDTO.class);
        dto.getUser().setFollowingAmount(followingAmount);
        return dto;
    }

    public JobCandidate toEntity(JobCandidateDTO dto){
        JobCandidate entity = mapper.map(dto, JobCandidate.class);
        return entity;
    }
    public JobCandidateMinDTO toMinDto(JobCandidate entity, Optional<Long> totalCandidates) {
        JobCandidateMinDTO dto = mapper.map(entity, JobCandidateMinDTO.class);
        if(entity.getJobRequest().getDateCreated() != null) {
            dto.getJobRequest().setDateCreated(this.dateTimeFormatter.format(entity.getJobRequest().getDateCreated()));
        }

        if(entity.getJobRequest().getDateTarget() != null) {
            dto.getJobRequest().setDateTarget(this.dateTimeFormatter.format(entity.getJobRequest().getDateTarget()));
        }

        if(entity.getDateCreated() != null){
            dto.setDate(this.dateTimeFormatter.format(entity.getDateCreated()));
        }
        dto.getJobRequest().setTotalCandidates(totalCandidates.get());
        dto.getJobRequest().setTextualDate(DateUtil.getTextualDate(entity.getJobRequest().getDateTarget()));

        return dto;
    }

}