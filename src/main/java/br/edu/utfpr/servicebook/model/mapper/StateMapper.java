package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.StateDTO;
import br.edu.utfpr.servicebook.model.entity.State;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StateMapper {
    @Autowired
    private ModelMapper mapper;

    public StateDTO toDto(State entity){
        StateDTO dto = mapper.map(entity, StateDTO.class);
        return dto;
    }

    public StateDTO toResponseDto(State entity) {
        StateDTO dto = mapper.map(entity, StateDTO.class);
        dto.setName(entity.getName());
        return dto;
    }
    
    public State toEntity(StateDTO dto) {
        State entity = mapper.map(dto, State.class);
        return entity;
    }
}
