package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.repository.*;
import br.edu.utfpr.servicebook.util.CPFUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class IndexService {

    @Autowired
    ExpertiseRepository expertiseRepository;

    @Autowired
    ProfessionalRepository professionalRepository;

    @Autowired
    ProfessionalExpertiseRepository professionalExpertiseRepository;

    @Autowired
    JobRequestRepository jobRequestRepository;

    @Autowired
    JobCandidateRepository jobCandidateRepository;

    @Autowired
    ClientRepository clientRepository;

    public void initialize(){
        Expertise expertise1 = new Expertise("Encanador");
        Expertise expertise2 = new Expertise("Eletricista");
        Expertise expertise3 = new Expertise("Pintor");
        Expertise expertise4 = new Expertise("Jardineiro");
        Expertise expertise5 = new Expertise("Pedreiro");
        expertiseRepository.saveAll(Arrays.asList(expertise1, expertise2, expertise3, expertise4, expertise5));

        Professional professional1 = new Professional("Darth Vader", "darth_vader@mail.com", "(42)9 9999-9999", CPFUtil.geraCPF());
        professional1.setBirthDate(new Date());
        professional1.setProfilePicture("https://i.imgur.com/owhNAKK.png");
        professional1.setDescription("Sua falta de fé é perturbadora.");
        professional1.setRating(5);
        professional1.setDenounceAmount(1);
        professionalRepository.save(professional1);

        Professional professional2 = new Professional("Chewbacca", "chewbacca@mail.com", "(42)9 9999-9999", CPFUtil.geraCPF());
        professional2.setBirthDate(new Date());
        professional2.setProfilePicture("https://i.imgur.com/owhNAKK.png");
        professional2.setDescription("Sua falta de fé é perturbadora.");
        professional2.setRating(5);
        professional2.setDenounceAmount(1);
        professionalRepository.save(professional2);

        ProfessionalExpertise professionalExpertise1 = new ProfessionalExpertise(professional1, expertise1);
        ProfessionalExpertise professionalExpertise2 = new ProfessionalExpertise(professional2, expertise2);
        professionalExpertiseRepository.saveAll(Arrays.asList(professionalExpertise1, professionalExpertise2));

        Client client1 = new Client("João Silva", "joao@mail.com", "(42)9 9999-9999", "85035-180");
        Client client2 = new Client("Thomaz Leite", "thomaz@mail.com", "(42)9 9999-9999", "85035-180");
        clientRepository.saveAll(Arrays.asList(client1, client2));

        JobRequest jb1 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, new Date());
        jb1.setExpertise(expertise1);
        jb1.setClient(client1);

        JobRequest jb2 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, new Date());
        jb1.setExpertise(expertise1);
        jb1.setClient(client2);

        jobRequestRepository.save(jb1);

        JobCandidate jobCandidate = new JobCandidate(jb1, professional1);
        jobCandidateRepository.save(jobCandidate);

    }

}
