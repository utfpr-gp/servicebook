package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.CompanyDTO;
import br.edu.utfpr.servicebook.model.dto.CompanyMinDTO;
import br.edu.utfpr.servicebook.model.dto.IndividualDTO;
import br.edu.utfpr.servicebook.model.dto.UserDTO;
import br.edu.utfpr.servicebook.model.entity.Company;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CompanyMapper {
    @Autowired
    private ModelMapper mapper;

    public CompanyDTO toDto(Company entity) {
        CompanyDTO dto = mapper.map(entity, CompanyDTO.class);
        return dto;
    }

    public CompanyDTO toResponseDto(Company entity) {
        CompanyDTO dto = mapper.map(entity, CompanyDTO.class);
        dto.setId(dto.getId());
        dto.setProfile(dto.getProfile());
        dto.setCnpj(dto.getCnpj());
        return dto;
    }

    public Company toEntity(CompanyDTO dto) {
        Company entity = mapper.map(dto, Company.class);
        return entity;
    }

    public CompanyMinDTO toMinDto(Company entity) {
        CompanyMinDTO dto = mapper.map(entity, CompanyMinDTO.class);
        return dto;
    }

}
