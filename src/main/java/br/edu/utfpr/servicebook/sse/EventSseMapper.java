package br.edu.utfpr.servicebook.sse;

import br.edu.utfpr.servicebook.model.dto.JobRequestFullDTO;
import br.edu.utfpr.servicebook.model.dto.JobRequestMinDTO;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EventSseMapper {

    @Autowired
    private ModelMapper mapper;

    public EventSseDTO toFullDto(EventSse entity){
        EventSseDTO dto = mapper.map(entity, EventSseDTO.class);
        dto.getId();
        dto.getDescriptionServ();
        dto.getMessage();
        dto.getFromProfessionalEmail();
        dto.getFromProfessionalName();
        return dto;
    }
}
