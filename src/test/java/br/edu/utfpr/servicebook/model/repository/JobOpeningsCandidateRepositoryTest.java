package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.util.CPFUtil;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class JobOpeningsCandidateRepositoryTest {

    @Autowired
    JobRequestRepository jobRequestRepository;

    @Autowired
    ExpertiseRepository expertiseRepository;

    @Autowired
    JobCandidateRepository jobCandidateRepository;

    @Autowired
    IndividualRepository individualRepository;

    @Autowired
    ProfessionalExpertiseRepository professionalExpertiseRepository;

    public static final Logger log =
            LoggerFactory.getLogger(JobOpeningsCandidateRepositoryTest.class);

    final LocalDate dateOfNow = LocalDate.now();

    @BeforeEach
    void setUp() {
        Expertise developerExpertise = new Expertise("Desenvolvedor de Software");
        developerExpertise = expertiseRepository.save(developerExpertise);

        Expertise mechanicExpertise = new Expertise("Mecânico de Motossera ");
        mechanicExpertise = expertiseRepository.save(mechanicExpertise);

        JobRequest jb1 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, dateOfNow);
        jb1.setExpertise(developerExpertise);

        JobRequest jb2 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, dateOfNow);
        jb2.setExpertise(mechanicExpertise);

        JobRequest jb3 = new JobRequest(JobRequest.Status.CLOSED, "", 10, dateOfNow);
        jb3.setExpertise(developerExpertise);

        JobRequest jb4 = new JobRequest(JobRequest.Status.CLOSED, "", 10, dateOfNow);
        jb4.setExpertise(mechanicExpertise);

        jb1 = jobRequestRepository.save(jb1);
        jb2 = jobRequestRepository.save(jb2);
        jobRequestRepository.save(jb3);
        jobRequestRepository.save(jb4);

        Individual joao = new Individual("Roberto Carlos", "joao@mail.com", "Senha123", "(42) 88999-9992", CPFUtil.geraCPF());
        joao = individualRepository.save(joao);

        ProfessionalExpertise professionalExpertise1 = new ProfessionalExpertise(joao, mechanicExpertise);
        professionalExpertiseRepository.save(professionalExpertise1);

        JobCandidate candidate1 = new JobCandidate(jb1, joao);
        jobCandidateRepository.save(candidate1);

        JobCandidate candidate2 = new JobCandidate(jb2, joao);
        candidate2.setChosenByBudget(true);
        jobCandidateRepository.save(candidate2);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar uma lista de candidaturas de um profissional")
    public void findByProfessional() {

        Optional<Individual> joao = individualRepository.findByEmail("joao@mail.com");
        List<JobCandidate> jobs = jobCandidateRepository.findByUser(joao.get());
        log.debug(jobs.toString());
        for(JobCandidate job : jobs){
            log.debug(job.getJobRequest().toString());
        }
        Assertions.assertFalse(jobs.isEmpty());
        Assertions.assertEquals(jobs.size(), 2);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar uma lista de candidaturas de um profissional escolhidas para fazer orçamento")
    public void findByProfessionalAndChosenByBudget() {

        Optional<Individual> joao = individualRepository.findByEmail("joao@mail.com");
        List<JobCandidate> jobs = jobCandidateRepository.findByUserAndChosenByBudget(joao.get(), true);
        log.debug(jobs.toString());
        Assertions.assertFalse(jobs.isEmpty());
        Assertions.assertEquals(jobs.size(), 1);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar uma lista de candidaturas para serviços em aberto de um profissional para qualquer especialidade")
    public void findByJobRequest_StatusAndProfessional() {
        JobRequest jb1 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, dateOfNow);
        jobRequestRepository.save(jb1);

        Optional<Individual> joao = individualRepository.findByEmail("joao@mail.com");

        JobCandidate candidate1 = new JobCandidate(jb1, joao.get());
        jobCandidateRepository.save(candidate1);

        List<JobCandidate> jobs = jobCandidateRepository.findByJobRequest_StatusAndUser(JobRequest.Status.AVAILABLE, joao.get());
        log.debug(jobs.toString());
        for(JobCandidate job : jobs){
            log.debug(job.getJobRequest().toString());
        }
        Assertions.assertFalse(jobs.isEmpty());
        Assertions.assertEquals(jobs.size(), 3);
    }

    @AfterEach
    void tearDown() {
        jobRequestRepository.deleteAll();
    }
}