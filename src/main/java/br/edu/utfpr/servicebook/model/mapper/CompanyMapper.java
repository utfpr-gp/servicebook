package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.Company;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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
        dto.setDescription(dto.getDescription());
        dto.setRating(dto.getRating());
        dto.setDenounceAmount(dto.getDenounceAmount());
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
    public ProfessionalSearchItemDTO toSearchItemDto(Company entity, List<ExpertiseDTO> expertises){
        ProfessionalSearchItemDTO dto = mapper.map(entity, ProfessionalSearchItemDTO.class);
        dto.setExpertises(expertises);
        return dto;
    }
}
