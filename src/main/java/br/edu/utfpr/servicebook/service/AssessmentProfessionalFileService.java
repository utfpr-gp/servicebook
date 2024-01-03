package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.AssessmentProfessionalFiles;
import br.edu.utfpr.servicebook.model.repository.AssessmentProfessionalFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AssessmentProfessionalFileService {

    @Autowired
    private AssessmentProfessionalFileRepository repository;

    public AssessmentProfessionalFiles save(AssessmentProfessionalFiles entity){
        return repository.save(entity);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public List<AssessmentProfessionalFiles> findAll(){
        return this.repository.findAll();
    }
}
