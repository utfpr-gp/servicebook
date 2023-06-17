package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.CompanyProfessionalDTO;
import br.edu.utfpr.servicebook.model.dto.CompanyProfessionalDTO2;
import br.edu.utfpr.servicebook.model.dto.ProfessionalExpertiseDTO2;
import br.edu.utfpr.servicebook.model.entity.CompanyProfessional;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CompanyProfessionalMapper {
    @Autowired
    private ModelMapper mapper;
    public CompanyProfessionalDTO2 toDTO(User entity) {
        return mapper.map(entity, CompanyProfessionalDTO2.class);
    }

    public CompanyProfessionalDTO2 toResponseDTO(CompanyProfessional entity) {
        CompanyProfessionalDTO2 dto = mapper.map(entity, CompanyProfessionalDTO2.class);
        dto.setId(entity.getId());
        dto.setName(entity.getProfessional().getName());
        dto.setEmail(entity.getProfessional().getEmail());
        dto.setProfilePicture(entity.getProfessional().getProfilePicture());
        dto.setIsConfirmed(String.valueOf(entity.getProfessional().isConfirmed()));
        return dto;
    }

    public CompanyProfessional toEntity(CompanyProfessionalDTO dto) {
        return mapper.map(dto, CompanyProfessional.class);
    }
}
