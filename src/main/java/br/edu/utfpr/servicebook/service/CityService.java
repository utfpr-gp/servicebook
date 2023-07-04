package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.State;
import br.edu.utfpr.servicebook.model.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<City> findAll(PageRequest pageRequest){
        return this.cityRepository.findAll(pageRequest);
    }

    public Optional<City> findById(Long id){
        return this.cityRepository.findById(id);
    }

    public Optional<City> findByNameAndState(String name, State state){
        return this.cityRepository.findByNameAndState(name, state);
    }

    public Optional<City> findByIdAndState(Long id, State state){
        return this.cityRepository.findByIdAndState(id, state);
    }

    public Optional<City> findByIdAndState_Id(Long cityId, Long stateId){
        return this.cityRepository.findByIdAndState_Id(cityId, stateId);
    }

    public Optional<City> findByName(String name){
        return this.cityRepository.findByName(name);
    }

}
