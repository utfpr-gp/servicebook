package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.AddressDTO;
import br.edu.utfpr.servicebook.model.dto.UserDTO;
import br.edu.utfpr.servicebook.model.entity.Address;
import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    @Autowired
    private ModelMapper mapper;

    public AddressDTO toDto(Address entity) {
        AddressDTO dto = mapper.map(entity, AddressDTO.class);
        return dto;
    }

    public Address toEntity(AddressDTO dto) {
        Address entity = mapper.map(dto, Address.class);
        return entity;
    }

    public Address toUpdate(AddressDTO dto, Long id, City city) {
        Address entity = mapper.map(dto, Address.class);
        entity.setId(id);
        entity.setCity(city);
        return entity;
    }

}
