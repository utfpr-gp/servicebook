package br.edu.utfpr.servicebook.model.mapper;


import br.edu.utfpr.servicebook.model.dto.UserCodeDTO;
import br.edu.utfpr.servicebook.model.entity.UserCode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCodeMapper {

    @Autowired
    private ModelMapper mapper;

    public UserCodeDTO toDto(UserCode entity) {
        UserCodeDTO dto = mapper.map(entity, UserCodeDTO.class);
        return dto;
    }

    public UserCode toEntity(UserCodeDTO dto) {
        UserCode entity = mapper.map(dto, UserCode.class);
        return entity;
    }

}
