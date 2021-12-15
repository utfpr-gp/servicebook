package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Professional;
import br.edu.utfpr.servicebook.model.repository.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalService {
    @Autowired
    private ProfessionalRepository professionalRepository;

    public Professional save(Professional entity){

        return professionalRepository.save(entity);
    }

    public void delete(Long id){

        professionalRepository.deleteById(id);
    }

    public List<Professional> findAll(){

        return this.professionalRepository.findAll();
    }
    public Optional<Professional> findById(Long id){

        return this.professionalRepository.findById(id);
    }

    public Professional findByEmailAddress(String email){

        return this.professionalRepository.findByEmailAddress(email);
    }

    public List<Professional> findAllByNameIgnoreCase(String name){
        return this.professionalRepository.findAllByNameIgnoreCase(name);
    }
}