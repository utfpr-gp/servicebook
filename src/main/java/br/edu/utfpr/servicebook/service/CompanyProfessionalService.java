package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.repository.CompanyProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyProfessionalService {

    @Autowired
    private CompanyProfessionalRepository companyProfessionalRepository;

    public Optional<CompanyProfessional> findByProfessionalAndCompany(User company, User individual) {
        return this.companyProfessionalRepository.findByProfessionalAndCompany(company, individual);
    }

    public CompanyProfessional save(CompanyProfessional companyProfessional) {
        return this.companyProfessionalRepository.save(companyProfessional);
    }

    public void delete(CompanyProfessionalPK professional_id){
        companyProfessionalRepository.deleteById(professional_id);
    }

    public List<User> findDistinctByTermIgnoreCase(String searchTerm){
        return this.companyProfessionalRepository.findDistinctByTermIgnoreCase(searchTerm);
    }

    public Optional<Integer> selectRatingByProfessionalAndExpertise(Long professional_id, Long expertise_id) {
        return this.companyProfessionalRepository.selectRatingByProfessionalAndExpertise(professional_id, expertise_id);
    }

    public Optional<CompanyProfessional> findByCompanyAndProfessional(User company, User professional) {
        return this.companyProfessionalRepository.findByProfessionalAndCompany(company, professional);
    }

    public List<CompanyProfessional> findByCompany(User user) {return this.companyProfessionalRepository.findByCompany(user);}

    public List<CompanyProfessional> findByProfessional(User user) {return this.companyProfessionalRepository.findByProfessional(user);}

}
