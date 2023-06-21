package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.ServiceDTO;
import br.edu.utfpr.servicebook.model.entity.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {
    @Autowired
    private ModelMapper mapper;

    public ServiceDTO toDto(Service entity){
        ServiceDTO dto = mapper.map(entity, ServiceDTO.class);
        return dto;
    }

    public Service toEntity(ServiceDTO dto){
        Service entity = mapper.map(dto, Service.class);
        return entity;
    }

}
