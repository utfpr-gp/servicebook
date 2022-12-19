package br.edu.utfpr.servicebook.follower;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    public Follow followNewClient(Long idFollowed, Long idFollower) throws JsonProcessingException {
        JSONArray idFollowerList = new JSONArray();
        idFollowerList.put(idFollower);
        Follow followObj = new Follow(idFollowed, idFollowerList.toString(), idFollowerList.length());
        return followObj;
    }

    public Follow followisClient(Follow followObj, Long idFollower) throws RuntimeException {
        JSONArray idFollowerList = new JSONArray(followObj.getFollowers_json());
        if (idFollowerList.length() <= 0){
            System.err.println("ENTROU NO IF ARRAY VAZIO.... " );
            idFollowerList.put(idFollower);
            followObj.setFollowers_json(idFollowerList.toString());
            followObj.setAmount_followers(idFollowerList.length());
            System.err.println("OBJETO PARA SER SALVADO... " + followObj.toString());
            return followObj;
        }
        if (idFollowerList.length() > 0 ) {
            System.err.println("ENTROU NO IF ARRAY PREENCHIDA.....");
            for (int i = 0; i < idFollowerList.length(); i++) {
                System.err.println("(" + i + ") " + idFollowerList.getInt(i));
                if (idFollowerList.getInt(i) == Integer.parseInt(idFollower.toString())) {
                    throw new RuntimeException("Usuario ja Inscrito");
                }
            }
            idFollowerList.put(idFollower);
            followObj.setFollowers_json(idFollowerList.toString());
            followObj.setAmount_followers(idFollowerList.length());
            System.err.println("OBJETO PARA SER SALVADO... " + followObj.toString());
            return followObj;
        }
        return followObj;
    }

    public Follow save(Follow entity) {
        return this.followRepository.save(entity);
    }



    //metodo seguir - com redirecionameto
    //get pagina lista de seguidores - pega seguidorees do banco e encaminha para uma pagina especifica
    //metodo deseguir
    //metodo quantidade de seguidores

}
