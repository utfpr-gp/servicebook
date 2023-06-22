package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Category;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.repository.ExpertiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpertiseService {
    @Autowired
    private ExpertiseRepository expertiseRepository;

    public Expertise save(Expertise entity){ return expertiseRepository.save(entity); }

    public List<Expertise> findAll() { return expertiseRepository.findAll(); }

    public Page<Expertise> findAll(PageRequest pageRequest) { return this.expertiseRepository.findAll(pageRequest); }

    public Optional<Expertise> findByName(String name){
        return this.expertiseRepository.findByName(name);
    }

    public Optional<Expertise> findById(Long id) {
        return this.expertiseRepository.findById(id);
    }

    public void delete(Long id) {
        this.expertiseRepository.deleteById(id);
    }


    public List<Expertise> findExpertiseNotExist(Long individual) {
        return this.expertiseRepository.findExpertiseNotExist(individual);
    }

    public Long countAll(){
        return this.expertiseRepository.countAll();
    }

    public Optional<Expertise> findByNameAndCategory(String name, Category category){
        return this.expertiseRepository.findByNameAndCategory(name, category);
    }

}