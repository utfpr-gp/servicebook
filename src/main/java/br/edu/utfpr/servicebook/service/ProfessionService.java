package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Profession;
import br.edu.utfpr.servicebook.model.repository.ProfessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessionService {
    @Autowired
    private ProfessionRepository professionRepository;

    public void save(Profession profession){ professionRepository.save(profession); }

    public List<Profession> findAll() { return professionRepository.findAll(); }

}
