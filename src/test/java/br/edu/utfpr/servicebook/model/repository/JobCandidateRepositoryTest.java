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
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class JobCandidateRepositoryTest {

    @Autowired
    JobRequestRepository jobRequestRepository;

    @Autowired
    ExpertiseRepository expertiseRepository;

    @Autowired
    JobCandidateRepository jobCandidateRepository;

    @Autowired
    ProfessionalRepository professionalRepository;

    @Autowired
    ProfessionalExpertiseRepository professionalExpertiseRepository;

    public static final Logger log =
            LoggerFactory.getLogger(JobCandidateRepositoryTest.class);

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

        JobRequest jb3 = new JobRequest(JobRequest.Status.CLOSED, "", 10, DateUtil.getNextWeek());
        jb3.setExpertise(developerExpertise);

        JobRequest jb4 = new JobRequest(JobRequest.Status.CLOSED, "", 10, DateUtil.getNextWeek());
        jb4.setExpertise(mechanicExpertise);

        jb1 = jobRequestRepository.save(jb1);
        jb2 = jobRequestRepository.save(jb2);
        jobRequestRepository.save(jb3);
        jobRequestRepository.save(jb4);

        Professional joao = new Professional("Roberto Carlos", "joao@mail.com", "", "", CPFUtil.geraCPF());
        joao = professionalRepository.save(joao);

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

        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");
        List<JobCandidate> jobs = jobCandidateRepository.findByProfessional(joao);
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

        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");
        List<JobCandidate> jobs = jobCandidateRepository.findByProfessionalAndChosenByBudget(joao, true);
        log.debug(jobs.toString());
        Assertions.assertFalse(jobs.isEmpty());
        Assertions.assertEquals(jobs.size(), 1);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar uma lista de candidaturas para serviços em aberto de um profissional para qualquer especialidade")
    public void findByJobRequest_StatusAndProfessional() {
        JobRequest jb1 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, DateUtil.getNextWeek());
        jobRequestRepository.save(jb1);

        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");

        JobCandidate candidate1 = new JobCandidate(jb1, joao);
        jobCandidateRepository.save(candidate1);

        List<JobCandidate> jobs = jobCandidateRepository.findByJobRequest_StatusAndProfessional(JobRequest.Status.AVAILABLE, joao);
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