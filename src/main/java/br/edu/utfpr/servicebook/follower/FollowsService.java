package br.edu.utfpr.servicebook.follower;

import br.edu.utfpr.servicebook.model.entity.Follows;
import br.edu.utfpr.servicebook.model.entity.Individual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowsService {

    @Autowired
    private FollowsRepository followsRepository;

    public List<Follows> findFollowsByProfessional(Individual professional) {
        return followsRepository.findFollowsByProfessional(professional);
    }

    public List<Follows> findFollowingByClient(Individual client) {return followsRepository.findFollowsByClient(client);}

    public List<Follows> findFollowProfessionalClient(Individual professional, Individual client){
        return followsRepository.isClientFollowProfessional(professional, client);
    }

    public Optional<Long> countByProfessional(Individual professional){
        return followsRepository.countByProfessional(professional);
    }

    public Optional<Long> countByClient(Individual client){
        return followsRepository.countByClient(client);
    }

    public List<Follows> findAll() { return followsRepository.findAll(); }

    public Follows save(Follows entity){ return followsRepository.save(entity); }

    public void delete(Follows follows){
        followsRepository.delete(follows);
    }
}
