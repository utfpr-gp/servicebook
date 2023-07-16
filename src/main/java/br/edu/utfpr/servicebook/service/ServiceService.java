package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Service;
import br.edu.utfpr.servicebook.model.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceService {
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

    public Optional<Service> findByNameAndExpertise(String name, Expertise expertise){
        return this.serviceRepository.findByNameAndExpertise(name, expertise);
    }

    /**
     * Busca os serviços de uma especialidade
     * @param expertise
     * @return
     */
    @Query("SELECT e FROM Service e WHERE e.expertise = :expertise")
    public List<Service> findByExpertise(Expertise expertise){
        return this.serviceRepository.findByExpertise(expertise);
    }
}
