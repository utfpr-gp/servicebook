package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Company;
import br.edu.utfpr.servicebook.model.repository.CompanyRepository;
import br.edu.utfpr.servicebook.model.repository.ProfessionalExpertiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProfessionalExpertiseRepository professionalExpertiseRepository;

    public void save(Company entity) {
        companyRepository.save(entity);
    }

    public List<Company> findAll() {
        return this.companyRepository.findAll();
    }

    public Optional<Company> findById(Long id) {
        return this.companyRepository.findById(id);
    }

    public Optional<Company> findByCnpj(String cnpj) {
        return this.companyRepository.findByCnpj(cnpj);
    }
}