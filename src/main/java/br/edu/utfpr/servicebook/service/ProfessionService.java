package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Profession;
import br.edu.utfpr.servicebook.model.repository.ProfessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionService {
    @Autowired
    private ProfessionRepository professionRepository;

    public Profession save(Profession entity){ return professionRepository.save(entity); }

    public List<Profession> findAll() { return professionRepository.findAll(); }

    public Page<Profession> findAll(PageRequest pageRequest) { return this.professionRepository.findAll(pageRequest); }

    public Optional<Profession> findByName(String name){
        return this.professionRepository.findByName(name);
    }

    public Optional<Profession> findById(Long id) {
        return this.professionRepository.findById(id);
    }

    public void delete(Long id) {
        this.professionRepository.deleteById(id);
    }
}
