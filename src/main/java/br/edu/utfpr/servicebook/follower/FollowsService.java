package br.edu.utfpr.servicebook.follower;

import br.edu.utfpr.servicebook.model.entity.Follows;
import br.edu.utfpr.servicebook.model.entity.Individual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowsService {

    @Autowired
    private FollowsRepository followsRepository;

//    public void delete(Long jobId, Long individualId) {
//        jobCandidateRepository.deleteById(jobId, individualId);
//    }
//
//    public Optional<JobCandidate> findById(Long jobId, Long individualId) {
//        return jobCandidateRepository.findByJobIdAndIndividualId(jobId, individualId);
//    }
//    public Integer getFollowsAmountbyIdProfessional(Long Idprofessional){
//        return followsRepository.getFollowsAmountbyIdProfessional(Idprofessional);
//    }

    public List<Follows> findFollowsByProfessional(Individual professional) {return followsRepository.findFollowsByProfessional(professional);}

    public List<Follows> findFollowsByClient(Individual client) {return followsRepository.findFollowsByClient(client);}

    public List<Follows> findAll() { return followsRepository.findAll(); }

    public Follows save(Follows entity){ return followsRepository.save(entity); }


//    public Follow followNewClient(Long idFollowed, Long idFollower) throws JsonProcessingException {
//        JSONArray idFollowerList = new JSONArray();
//        idFollowerList.put(idFollower);
//        Follow followObj = new Follow(idFollowed, idFollowerList.toString(), idFollowerList.length());
//        return followObj;
//    }

//    public Follow followisClient(Follow followObj, Long idFollower) throws RuntimeException {
//        JSONArray idFollowerList = new JSONArray(followObj.getFollowers_json());
//        if (idFollowerList.length() <= 0){
//            System.err.println("ENTROU NO IF ARRAY VAZIO.... " );
//            idFollowerList.put(idFollower);
//            followObj.setFollowers_json(idFollowerList.toString());
//            followObj.setAmount_followers(idFollowerList.length());
//            System.err.println("OBJETO PARA SER SALVADO... " + followObj.toString());
//            return followObj;
//        }
//        if (idFollowerList.length() > 0 ) {
//            System.err.println("ENTROU NO IF ARRAY PREENCHIDA.....");
//            for (int i = 0; i < idFollowerList.length(); i++) {
//                System.err.println("(" + i + ") " + idFollowerList.getInt(i));
//                if (idFollowerList.getInt(i) == Integer.parseInt(idFollower.toString())) {
//                    throw new RuntimeException("Usuario ja Inscrito");
//                }
//            }
//            idFollowerList.put(idFollower);
//            followObj.setFollowers_json(idFollowerList.toString());
//            followObj.setAmount_followers(idFollowerList.length());
//            System.err.println("OBJETO PARA SER SALVADO... " + followObj.toString());
//            return followObj;
//        }
//        return followObj;
//    }


//    public Follow unFollowisClient(Follow followObj, Long idFollower) throws RuntimeException {
//        JSONArray idFollowerList = new JSONArray(followObj.getFollowers_json());
//
//            for (int i = 0; i < idFollowerList.length(); i++) {
//                System.out.println("(" + i + ") " + idFollowerList.getInt(i));
//                if (idFollowerList.getInt(i) == Integer.parseInt(idFollower.toString())) {
//                    idFollowerList.remove(i);
//                }
//            }
//            followObj.setFollowers_json(idFollowerList.toString());
//            followObj.setAmount_followers(idFollowerList.length());
//            System.err.println("OBJETO PARA SER SALVADO... " + followObj.toString());
//            return followObj;
//    }

//    public Follow getFollowedById(Long idFollowed) {
//        Follow followObj = followRepository.findFollowsByidFollowed(idFollowed);
//        //JSONArray idFollowerList = new JSONArray(followObj.getFollowers_json());
//
//        return followObj;
//    }


//    public Follow save(Follow entity) {
//        return this.followRepository.save(entity);
//    }
}
