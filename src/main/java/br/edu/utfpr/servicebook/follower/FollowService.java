package br.edu.utfpr.servicebook.follower;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    public void follow(Long idFollowed, Long idFollower){
        followRepository.findPendingFollowsByidFollowed(idFollowed);
        System.err.println("Teste mostrando objeto retronado do bacno..." + followRepository.findPendingFollowsByidFollowed(idFollowed));

    }


    //metodo seguir - com redirecionameto
    //get pagina lista de seguidores - pega seguidorees do banco e encaminha para uma pagina especifica
    //metodo deseguir
    //metodo quantidade de seguidores

}
