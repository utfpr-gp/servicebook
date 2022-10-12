package br.edu.utfpr.servicebook.model.mapper;


import br.edu.utfpr.servicebook.model.dto.UserDTO;
import br.edu.utfpr.servicebook.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper mapper;

    public UserDTO toDto(User entity) {
        UserDTO dto = mapper.map(entity, UserDTO.class);
        return dto;
    }

    public User toEntity(UserDTO dto) {
        User entity = mapper.map(dto, User.class);
        return entity;
    }

}
