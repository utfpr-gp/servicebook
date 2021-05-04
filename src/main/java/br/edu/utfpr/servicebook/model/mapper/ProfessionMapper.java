package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.ProfessionDTO;
import br.edu.utfpr.servicebook.model.entity.Profession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfessionMapper {

    @Autowired
    private ModelMapper mapper;

    public ProfessionDTO toDto(Profession entity){
        ProfessionDTO dto = mapper.map(entity, ProfessionDTO.class);
        return dto;
    }

    public Profession toEntity(ProfessionDTO dto){
        Profession entity = mapper.map(dto, Profession.class);
        return entity;
    }
}
