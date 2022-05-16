package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.repository.*;
import br.edu.utfpr.servicebook.util.CPFUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class IndexService {

    @Autowired
    ExpertiseRepository expertiseRepository;

    @Autowired
    IndividualRepository individualRepository;

    @Autowired
    ProfessionalExpertiseRepository professionalExpertiseRepository;

    @Autowired
    JobRequestRepository jobRequestRepository;

    @Autowired
    JobCandidateRepository jobCandidateRepository;

    @Autowired
    IndividualRepository clientRepository;

    public void initialize(){
        Expertise expertise1 = new Expertise("Encanador");
        Expertise expertise2 = new Expertise("Eletricista");
        Expertise expertise3 = new Expertise("Pintor");
        Expertise expertise4 = new Expertise("Jardineiro");
        Expertise expertise5 = new Expertise("Pedreiro");
        expertiseRepository.saveAll(Arrays.asList(expertise1, expertise2, expertise3, expertise4, expertise5));

        Individual individual1 = new Individual("Darth Vader", "darth_vader@mail.com", "Senha123", "(42)9 9999-9999", CPFUtil.geraCPF());
        individual1.setBirthDate(new Date());
        individual1.setProfilePicture("https://i.imgur.com/owhNAKK.png");
        individual1.setDescription("Sua falta de fé é perturbadora.");
        individual1.setRating(5);
        individual1.setDenounceAmount(1);
        individualRepository.save(individual1);

        Individual individual2 = new Individual("Chewbacca", "chewbacca@mail.com", "Senha123", "(42)9 9999-9999", CPFUtil.geraCPF());
        individual2.setBirthDate(new Date());
        individual2.setProfilePicture("https://i.imgur.com/owhNAKK.png");
        individual2.setDescription("Sua falta de fé é perturbadora.");
        individual2.setRating(5);
        individual2.setDenounceAmount(1);
        individualRepository.save(individual2);

        ProfessionalExpertise professionalExpertise1 = new ProfessionalExpertise(individual1, expertise1);
        ProfessionalExpertise professionalExpertise2 = new ProfessionalExpertise(individual2, expertise2);
        professionalExpertiseRepository.saveAll(Arrays.asList(professionalExpertise1, professionalExpertise2));

        Individual client1 = new Individual("João Silva", "joao@mail.com", "qwerty123", "(42)9 9999-9999", "85035-180");
        Individual client2 = new Individual("Thomaz Leite", "thomaz@mail.com", "qwerty123", "(42)9 9999-9999", "85035-180");
        clientRepository.saveAll(Arrays.asList(client1, client2));

        JobRequest jb1 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, new Date());
        jb1.setExpertise(expertise1);
        jb1.setIndividual(client1);

        JobRequest jb2 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, new Date());
        jb1.setExpertise(expertise1);
        jb1.setIndividual(client2);

        jobRequestRepository.save(jb1);

        JobCandidate jobCandidate = new JobCandidate(jb1, individual1);
        jobCandidateRepository.save(jobCandidate);

    }

}
