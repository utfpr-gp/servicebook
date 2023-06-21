package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Service;
import br.edu.utfpr.servicebook.model.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServicesService {
    @Autowired
    private ServiceRepository serviceRepository;

    public Service save(Service entity){ return serviceRepository.save(entity); }

    public List<Service> findAll() { return serviceRepository.findAll(); }

    public Page<Service> findAll(PageRequest pageRequest) { return this.serviceRepository.findAll(pageRequest); }

    public Optional<Service> findByName(String name){
        return this.serviceRepository.findByName(name);
    }

    public Optional<Service> findById(Long id) {
        return this.serviceRepository.findById(id);
    }

    public void delete(Long id) {
        this.serviceRepository.deleteById(id);
    }

}
