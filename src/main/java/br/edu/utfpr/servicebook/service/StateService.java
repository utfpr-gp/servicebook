package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.State;
import br.edu.utfpr.servicebook.model.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    public State save(State entity){

        return stateRepository.save(entity);
    }

    public void delete(Long id){

        stateRepository.deleteById(id);
    }

    public List<State> findAll(){
        return this.stateRepository.findAll();
    }

    public Optional<State> findById(Long id){
        return this.stateRepository.findById(id);
    }


}
