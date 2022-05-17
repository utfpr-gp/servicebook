package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.ClientDTO;
import br.edu.utfpr.servicebook.model.entity.Individual;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    @Autowired
    private ModelMapper mapper;

    public ClientDTO toDto(Individual entity){
        ClientDTO dto = mapper.map(entity, ClientDTO.class);
        return dto;
    }

    public ClientDTO toResponseDto(Individual entity) {
        ClientDTO dto = mapper.map(entity, ClientDTO.class);
        return dto;
    }

    public Individual toEntity(ClientDTO dto) {
        Individual entity = mapper.map(dto, Individual.class);
        return entity;
    }
}