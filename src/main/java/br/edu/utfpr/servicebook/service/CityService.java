package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.State;
import br.edu.utfpr.servicebook.model.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public City save(City entity){

        return cityRepository.save(entity);
    }

    public void delete(Long id){

        cityRepository.deleteById(id);
    }

    public List<City> findAll(){

        return this.cityRepository.findAll();
    }

    public Optional<City> findById(Long id){

        return this.cityRepository.findById(id);
    }

    public Optional<City> findByNameAndState(String name, State state){
        return this.cityRepository.findByNameAndState(name, state);
    }

}
