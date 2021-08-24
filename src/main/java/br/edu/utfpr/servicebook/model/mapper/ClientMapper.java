package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.ClientDTO;
import br.edu.utfpr.servicebook.model.entity.Client;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    @Autowired
    private ModelMapper mapper;

    public ClientDTO toDto(Client entity){
        ClientDTO dto = mapper.map(entity, ClientDTO.class);
        return dto;
    }

    public ClientDTO toResponseDto(Client entity) {
        ClientDTO dto = mapper.map(entity, ClientDTO.class);
        return dto;
    }

    public Client toEntity(ClientDTO dto) {
        Client entity = mapper.map(dto, Client.class);
        return entity;
    }
}