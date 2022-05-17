package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.repository.ExpertiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public List<Expertise> search(String keyword) {
        if(keyword != null){
            return this.expertiseRepository.search(keyword);
        }
        return expertiseRepository.findAll();
    }

}