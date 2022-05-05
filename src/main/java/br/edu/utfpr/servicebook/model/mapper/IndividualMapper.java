package br.edu.utfpr.servicebook.model.mapper;


import br.edu.utfpr.servicebook.model.dto.IndividualDTO;
import br.edu.utfpr.servicebook.model.dto.IndividualMinDTO;
import br.edu.utfpr.servicebook.model.entity.Individual;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IndividualMapper {

    @Autowired
    private ModelMapper mapper;

    public IndividualDTO toDto(Individual entity) {
        IndividualDTO dto = mapper.map(entity, IndividualDTO.class);
        return dto;
    }

    public IndividualMinDTO toMinDto(Individual entity){
        IndividualMinDTO dto = mapper.map(entity, IndividualMinDTO.class);
        return dto;
    }

    public Individual toEntity(IndividualDTO dto) {
        Individual entity = mapper.map(dto, Individual.class);
        return entity;
    }

}
