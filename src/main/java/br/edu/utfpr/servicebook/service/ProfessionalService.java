package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.repository.IndividualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalService {
    @Autowired
    private IndividualRepository individualRepository;

    public Individual save(Individual entity){

        return individualRepository.save(entity);
    }

    public void delete(Long id){

        individualRepository.deleteById(id);
    }

    public List<Individual> findAll(){

        return this.individualRepository.findAll();
    }
    public Optional<Individual> findById(Long id){

        return this.individualRepository.findById(id);
    }

    public Individual findByEmail(String email){

        return this.individualRepository.findByEmail(email);
    }

    public Optional<Individual> findByCpf(String cpf) {
        return this.individualRepository.findByCpf(cpf);
    }
}