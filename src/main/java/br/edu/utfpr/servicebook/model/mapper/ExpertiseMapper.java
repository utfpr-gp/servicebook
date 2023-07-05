package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.ExpertiseMinDTO;
import br.edu.utfpr.servicebook.model.dto.ProfessionalSearchItemDTO;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Individual;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ExpertiseMapper {

    @Autowired
    private ModelMapper mapper;

    TypeMap<ExpertiseDTO, Expertise> typeMap;

    @PostConstruct
    public void init(){
//        typeMap = mapper.createTypeMap(ExpertiseDTO.class, Expertise.class);
//        typeMap.addMappings(mapper -> mapper.skip(Expertise::setCategory));
//        typeMap.addMappings(mapper -> mapper.skip(ExpertiseDTO::getCategoryName, Expertise::setCategory));
//        typeMap.addMappings(mapper -> mapper.skip(ExpertiseDTO::getCategoryId, Expertise::setCategory));
    }

    public ExpertiseDTO toDto(Expertise entity){
        ExpertiseDTO dto = mapper.map(entity, ExpertiseDTO.class);
        dto.setCategoryName(entity.getCategory().getName());
        return dto;
    }

    public Expertise toEntity(ExpertiseDTO dto){
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Expertise entity = mapper.map(dto, Expertise.class);
        return entity;
    }

    public ExpertiseMinDTO toMinDto(Expertise entity){
        ExpertiseMinDTO dto = mapper.map(entity, ExpertiseMinDTO.class);
        return dto;
    }
}
