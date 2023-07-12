package br.edu.utfpr.servicebook.model.mapper;


import br.edu.utfpr.servicebook.model.dto.JobRequestDTO;
import br.edu.utfpr.servicebook.model.dto.JobRequestDetailsDTO;
import br.edu.utfpr.servicebook.model.dto.JobRequestFullDTO;
import br.edu.utfpr.servicebook.model.dto.JobRequestMinDTO;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.util.DateUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class JobRequestMapper {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    public JobRequestDTO toDto(JobRequest entity) {
        return mapper.map(entity, JobRequestDTO.class);
    }

    public JobRequest toEntity(JobRequestDTO dto) {
        //Fazer ignorar
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(dto, JobRequest.class);
    }

    public JobRequestDetailsDTO jobRequestDetailsDTO(JobRequest entity){
        JobRequestDetailsDTO dto = mapper.map(entity, JobRequestDetailsDTO.class);
        dto.setDateCreated(this.dateTimeFormatter.format(entity.getDateCreated()));
        dto.setDateTarget(this.dateTimeFormatter.format(entity.getDateTarget()));
        dto.setTextualDate(DateUtil.getTextualDate((entity.getDateTarget())));

        return dto;
    }

    public JobRequestMinDTO toMinDto(JobRequest entity, Long amountOfCandidates) {
        JobRequestMinDTO dto = mapper.map(entity, JobRequestMinDTO.class);
        dto.setAmountOfCandidates(amountOfCandidates);
        dto.setExpertiseDTO(expertiseMapper.toDto(entity.getExpertise()));
        dto.setDateCreated(this.dateTimeFormatter.format(entity.getDateCreated()));
        dto.setDateTarget(this.dateTimeFormatter.format(entity.getDateTarget()));

        return dto;
    }

    public JobRequestFullDTO toFullDto(JobRequest entity){
        JobRequestFullDTO dto = mapper.map(entity, JobRequestFullDTO.class);
        dto.setDateCreated(this.dateTimeFormatter.format(entity.getDateCreated()));
        dto.setDateTarget(this.dateTimeFormatter.format(entity.getDateTarget()));
        return dto;
    }

    public JobRequestFullDTO toFullDto(JobRequest entity, Optional<Long> totalCandidates) {
        JobRequestFullDTO dto = mapper.map(entity, JobRequestFullDTO.class);
        dto.setTotalCandidates(totalCandidates.get());
        dto.setDateCreated(this.dateTimeFormatter.format(entity.getDateCreated()));
        dto.setDateTarget(this.dateTimeFormatter.format(entity.getDateTarget()));
        dto.setTextualDate(DateUtil.getTextualDate((entity.getDateTarget())));

        return dto;
    }

    public JobRequestFullDTO emptyToFullDto(JobRequest entity) {
        JobRequestFullDTO dto = mapper.map(entity, JobRequestFullDTO.class);
        return dto;
    }

}
