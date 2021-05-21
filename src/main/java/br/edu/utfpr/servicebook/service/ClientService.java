package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Client;

import br.edu.utfpr.servicebook.model.repository.ClientRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ClientService {


    @Autowired
    private ClientRepository clientRepository;

    public Client save(Client entity){
        return clientRepository.save(entity);
    }

    public void delete(Long id){
        clientRepository.deleteById(id);
    }

    public List<Client> findAll(){
        return this.clientRepository.findAll();
    }

    public void init() {

    }
}
