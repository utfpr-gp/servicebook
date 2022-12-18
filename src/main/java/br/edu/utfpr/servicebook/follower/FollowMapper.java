package br.edu.utfpr.servicebook.follower;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {

    @Autowired
    private ModelMapper mapper;


    public FollowDTO toFullDto(Follow entity){
        FollowDTO dto = mapper.map(entity, FollowDTO.class);
        dto.getId();
        dto.getFollowed_id();
        dto.getFollowed_id();
        dto.getAmountFollowers();
        return dto;
    }
}
