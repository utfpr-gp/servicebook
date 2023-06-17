package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.UserCodeDTO;
import br.edu.utfpr.servicebook.model.dto.UserTokenDTO;
import br.edu.utfpr.servicebook.model.entity.UserCode;
import br.edu.utfpr.servicebook.model.entity.UserToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTokenMapper {
    @Autowired
    private ModelMapper mapper;

    public UserTokenDTO toDto(UserToken entity) {
        UserTokenDTO dto = mapper.map(entity, UserTokenDTO.class);
        return dto;
    }

    public UserToken toEntity(UserTokenDTO dto) {
        UserToken entity = mapper.map(dto, UserToken.class);
        return entity;
    }
}
