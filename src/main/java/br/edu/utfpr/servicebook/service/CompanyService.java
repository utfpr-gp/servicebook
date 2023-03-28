package br.edu.utfpr.servicebook.service;
import br.edu.utfpr.servicebook.model.entity.Company;
import br.edu.utfpr.servicebook.model.entity.CompanyExpertise;
import br.edu.utfpr.servicebook.model.repository.CompanyExpertiseRepository;
import br.edu.utfpr.servicebook.model.repository.CompanyRepository;
import br.edu.utfpr.servicebook.security.IAuthentication;
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
    private IAuthentication authentication;

    @Autowired
    private CompanyExpertiseRepository companyExpertiseRepository;

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
    public Optional<Company> findByEmail(String email) {
        return this.companyRepository.findByEmail(email);
    }

    @Transactional
    public void saveExpertisesCompany(Company company, CompanyExpertise companyExpertise) {
        companyRepository.save(company);
        companyExpertiseRepository.save(companyExpertise);
    }
    public boolean isAuthenticated(){
        Optional<Company> oCompany = findByEmail(authentication.getEmail());

        if (!oCompany.isPresent()) {
            return false;
        }

        return true;
    }
    /**
     * Retorna o usuário autenticado ou nulo.
     * @return
     */
    public Company getAuthenticated(){
        Optional<Company> oCompany = findByEmail(authentication.getEmail());

        if (oCompany.isPresent()) {
            return oCompany.get();
        }

        return null;
    }
}
