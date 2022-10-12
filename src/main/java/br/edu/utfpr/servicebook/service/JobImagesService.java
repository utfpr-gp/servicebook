package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.JobImages;
import br.edu.utfpr.servicebook.model.repository.JobImagesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class JobImagesService {

    @Autowired
    private JobImagesRepository repository;

    public JobImages save(JobImages entity){
        return repository.save(entity);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public List<JobImages> findAll(){
        return this.repository.findAll();
    }
}
