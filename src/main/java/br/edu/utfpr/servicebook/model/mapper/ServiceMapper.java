package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.ServiceDTO;
import br.edu.utfpr.servicebook.model.entity.Service;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {
    @Autowired
    private ModelMapper mapper;

    public ServiceDTO toDto(Service entity){
        ServiceDTO dto = mapper.map(entity, ServiceDTO.class);
        dto.setExpertiseId(entity.getExpertise().getId());
        return dto;
    }

    public Service toEntity(ServiceDTO dto){
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Service entity = mapper.map(dto, Service.class);
        return entity;
    }

}
