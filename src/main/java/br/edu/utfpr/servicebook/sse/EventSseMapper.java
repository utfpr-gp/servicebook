package br.edu.utfpr.servicebook.sse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventSseMapper {

    @Autowired
    private ModelMapper mapper;

    public EventSseDTO toFullDto(EventSse entity){
        EventSseDTO dto = mapper.map(entity, EventSseDTO.class);
        dto.getId();
        dto.getDescriptionServ();
        dto.getMessage();
        dto.getFromEmail();
        dto.getFromName();
        return dto;
    }
}
