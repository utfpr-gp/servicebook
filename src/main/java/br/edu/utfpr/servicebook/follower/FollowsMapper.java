package br.edu.utfpr.servicebook.follower;


import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.entity.Follows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FollowsMapper {

    @Autowired
    private ModelMapper mapper;

    public Follows toEntity(FollowsDTO dto){
        Follows entity = mapper.map(dto, Follows.class);
        return entity;
    }

    public FollowsDTO toDto(Follows entity){
        FollowsDTO dto = mapper.map(entity, FollowsDTO.class);
        return dto;
    }

    public Follows optionalToEntity(Optional dto){
        Follows entity = mapper.map(dto, Follows.class);
        return entity;
    }

    public FollowsDTO toFullDto(Follows entity){
        FollowsDTO dto = mapper.map(entity, FollowsDTO.class);
        dto.getClient();
        dto.getProfessional();
        return dto;
    }
}
