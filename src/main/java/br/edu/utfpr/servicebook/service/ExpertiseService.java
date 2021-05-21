package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Expertise;

import br.edu.utfpr.servicebook.model.entity.Professional;
import br.edu.utfpr.servicebook.model.repository.ExpertiseRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ExpertiseService {


    @Autowired
    private ExpertiseRepository expertiseRepository;

    public Expertise save(Expertise entity){
        return expertiseRepository.save(entity);
    }

    public void delete(Long id){
        expertiseRepository.deleteById(id);
    }

    public List<Expertise> findAll(){
        return this.expertiseRepository.findAll();
    }

    public Expertise findByID(Long id){

        return this.expertiseRepository.findByID(id);
    }
    public void init() {

    }
}
