package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.repository.CompanyExpertiseRepository;
import br.edu.utfpr.servicebook.model.repository.ProfessionalExpertiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CompanyExpertiseService {

    @Autowired
    private CompanyExpertiseRepository companyExpertiseRepository;


    public Optional<CompanyExpertise> findByCompanyAndExpertise(Company company, Expertise expertise) {
        return this.companyExpertiseRepository.findByCompanyAndExpertise(company, expertise);
    }

    public List<CompanyExpertise> findByProfessional(Company individual) {
        return this.companyExpertiseRepository.findByCompany(individual);
    }

}
