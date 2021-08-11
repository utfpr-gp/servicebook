package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.util.CPFUtil;
import br.edu.utfpr.servicebook.util.DateUtil;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class JobRequestRepositoryTest {

    @Autowired
    JobRequestRepository jobRequestRepository;

    @Autowired
    ExpertiseRepository expertiseRepository;

    @Autowired
    JobCandidateRepository jobCandidateRepository;

    @Autowired
    ProfessionalRepository professionalRepository;

    public static final Logger log =
            LoggerFactory.getLogger(JobRequestRepositoryTest.class);

    @BeforeEach
    void setUp() {
        Expertise developerExpertise = new Expertise("Desenvolvedor de Software");
        developerExpertise = expertiseRepository.save(developerExpertise);

        Expertise mechanicExpertise = new Expertise("Mecânico");
        mechanicExpertise = expertiseRepository.save(mechanicExpertise);

        JobRequest jb1 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, DateUtil.getNextWeek());
        jb1.setExpertise(developerExpertise);

        JobRequest jb2 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, DateUtil.getNextWeek());
        jb2.setExpertise(mechanicExpertise);

        //ninguém se candidatou a este
        JobRequest jb3 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, DateUtil.getNextWeek());
        jb3.setExpertise(developerExpertise);

        JobRequest jb4 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, DateUtil.getNextWeek());
        jb4.setExpertise(mechanicExpertise);

        JobRequest jb5 = new JobRequest(JobRequest.Status.CLOSED, "", 10, DateUtil.getNextWeek());
        jb4.setExpertise(mechanicExpertise);

        jobRequestRepository.save(jb1);
        jobRequestRepository.save(jb2);
        jobRequestRepository.save(jb3);
        jobRequestRepository.save(jb4);
        jobRequestRepository.save(jb5);

        //João Mecânico
        Professional joao = new Professional("Roberto Carlos", "joao@mail.com", "", "", CPFUtil.geraCPF());
        joao = professionalRepository.save(joao);
        joao.getExpertises().add(mechanicExpertise);
        joao = professionalRepository.save(joao);

        //Maria Desenvolvedora
        Professional maria = new Professional("Maria", "maria@mail.com", "", "", CPFUtil.geraCPF());
        maria = professionalRepository.save(maria);
        maria.getExpertises().add(developerExpertise);
        maria = professionalRepository.save(maria);

        JobCandidatePK candidatePK1 = new JobCandidatePK(jb2.getId(), joao.getId());
        JobCandidate candidate1 = new JobCandidate(candidatePK1, jb2, joao);
        jobCandidateRepository.save(candidate1);

        //joão foi escolhido para orçamento para o jb2
        JobCandidatePK candidatePK2 = new JobCandidatePK(jb4.getId(), joao.getId());
        JobCandidate candidate2 = new JobCandidate(candidatePK2, jb4, joao);
        candidate2.setChosenByBudget(true);
        jobCandidateRepository.save(candidate2);

        //maria se candidatou a desenvolvedora
        JobCandidatePK candidatePK3 = new JobCandidatePK(jb1.getId(), maria.getId());
        JobCandidate candidate3 = new JobCandidate(candidatePK3, jb1, maria);
        jobCandidateRepository.save(candidate3);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar uma lista de requisições disponíveis para uma especialidade")
    public void findByStatusAvailableAndExpertise() {
        Optional<Expertise> mecanico = expertiseRepository.findByName("Mecânico");
        List<JobRequest> jobs = jobRequestRepository.findByStatusAndExpertise(JobRequest.Status.AVAILABLE, mecanico.get());
        log.debug(jobs.toString());
        Assertions.assertFalse(jobs.isEmpty());
        Assertions.assertEquals(jobs.size(), 2);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar uma lista de requisições disponíveis para uma especialidade que um profissional se candidatou")
    public void findByStatusAndExpertiseAndCandidates() {
        Optional<Expertise> mecanico = expertiseRepository.findByName("Mecânico");
        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");
        List<JobRequest> jobs = jobRequestRepository.findByStatusAndExpertiseAndJobCandidates_Professional(JobRequest.Status.AVAILABLE, mecanico.get(), joao);
        log.debug(jobs.toString());
        Assertions.assertFalse(jobs.isEmpty());
        Assertions.assertEquals(jobs.size(), 2);
    }

    @AfterEach
    void tearDown() {
        jobRequestRepository.deleteAll();
    }
}