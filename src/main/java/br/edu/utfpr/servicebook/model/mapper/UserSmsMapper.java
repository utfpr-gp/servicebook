package br.edu.utfpr.servicebook.model.mapper;


import br.edu.utfpr.servicebook.model.dto.UserSmsDTO;
import br.edu.utfpr.servicebook.model.entity.UserSms;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSmsMapper {

    @Autowired
    private ModelMapper mapper;

    public UserSmsDTO toDto(UserSms entity) {
        UserSmsDTO dto = mapper.map(entity, UserSmsDTO.class);
        return dto;
    }

    public UserSms toEntity(UserSmsDTO dto) {
        UserSms entity = mapper.map(dto, UserSms.class);
        return entity;
    }

}
