package br.edu.utfpr.servicebook.sse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventSseMapper {

    @Autowired
    private ModelMapper mapper;

    public EventSSEDTO toFullDto(EventSSE entity){
        EventSSEDTO dto = mapper.map(entity, EventSSEDTO.class);
        dto.getId();
        dto.getDescriptionServ();
        dto.getMessage();
        dto.getFromEmail();
        dto.getFromName();
        return dto;
    }
}
