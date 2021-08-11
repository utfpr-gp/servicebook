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

    public static final Logger log =
            LoggerFactory.getLogger(JobCandidateRepositoryTest.class);

//    @BeforeEach
//    void setUp() {
//        Expertise e1 = new Expertise("Desenvolvedor de Software");
//        Expertise e2 = new Expertise("Mecânico");
//        e1 = expertiseRepository.save(e1);
//        e2 = expertiseRepository.save(e2);
//
//        JobRequest jb1 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, DateUtil.getNextWeek());
//        jb1.setExpertise(e1);
//        JobRequest jb2 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, DateUtil.getNextWeek());
//        jb2.setExpertise(e2);
//        JobRequest jb3 = new JobRequest(JobRequest.Status.CLOSED, "", 10, DateUtil.getNextWeek());
//        jb3.setExpertise(e1);
//        JobRequest jb4 = new JobRequest(JobRequest.Status.CLOSED, "", 10, DateUtil.getNextWeek());
//        jb4.setExpertise(e2);
//
//        jb1 = jobRequestRepository.save(jb1);
//        jb2 = jobRequestRepository.save(jb2);
//        jobRequestRepository.save(jb3);
//        jobRequestRepository.save(jb4);
//
//        Professional joao = new Professional("Roberto Carlos", "joao@mail.com", "", "", CPFUtil.geraCPF());
//        joao = professionalRepository.save(joao);
//
//        joao.getExpertises().add(e2);
//        joao = professionalRepository.save(joao);
//
//        JobCandidatePK candidatePK1 = new JobCandidatePK(jb1, joao);
//        JobCandidatePK candidatePK2 = new JobCandidatePK(jb2, joao);
//        JobCandidate candidate1 = new JobCandidate(candidatePK1);
//        //joão foi escolhido para orçamento para o jb2
//        JobCandidate candidate2 = new JobCandidate(candidatePK2);
//        candidate2.setChosenByBudget(true);
//
//        jobCandidateRepository.save(candidate1);
//        jobCandidateRepository.save(candidate2);
//
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("Deve retornar uma lista de candidaturas de um profissional")
//    public void findById_Professional() {
//
//        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");
//        List<JobCandidate> jobs = jobCandidateRepository.findById_Professional(joao);
//        log.debug(jobs.toString());
//        for(JobCandidate job : jobs){
//            log.debug(job.getId().getJobRequest().toString());
//        }
//        Assertions.assertFalse(jobs.isEmpty());
//        Assertions.assertEquals(jobs.size(), 1);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("Deve retornar uma lista de candidaturas de um profissional escolhidas para fazer orçamento")
//    public void findById_ProfessionalAndChosenByBudget() {
//
//        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");
//        List<JobCandidate> jobs = jobCandidateRepository.findById_ProfessionalAndChosenByBudget(joao, true);
//        log.debug(jobs.toString());
//        Assertions.assertFalse(jobs.isEmpty());
//        Assertions.assertEquals(jobs.size(), 1);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("Deve retornar uma lista de candidaturas para serviços em aberto de um profissional para qualquer especialidade")
//    public void findById_JobRequestStatusAndId_Professional() {
//        JobRequest jb1 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, DateUtil.getNextWeek());
//        jobRequestRepository.save(jb1);
//
//        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");
//
//        JobCandidatePK candidatePK1 = new JobCandidatePK(jb1, joao);
//        JobCandidate candidate1 = new JobCandidate(candidatePK1);
//        jobCandidateRepository.save(candidate1);
//
//        List<JobCandidate> jobs = jobCandidateRepository.findById_JobRequestStatusAndId_Professional(JobRequest.Status.AVAILABLE, joao);
//        System.err.println("QUANTIDADE: " + jobs.size());
//        log.debug(jobs.toString());
//        for(JobCandidate job : jobs){
//            log.debug(job.getId().getJobRequest().toString());
//        }
//        Assertions.assertFalse(jobs.isEmpty());
//        Assertions.assertEquals(jobs.size(), 3);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("Deve retornar uma lista de candidaturas para serviços em aberto de uma especialidade")
//    public void findById_JobRequest_StatusAndAndId_JobRequest_Expertise() {
//        Optional<Expertise> mecanico = expertiseRepository.findByName("Mecânico");
//
//        List<JobCandidate> jobs = jobCandidateRepository.findById_JobRequest_StatusAndAndId_JobRequest_Expertise(JobRequest.Status.AVAILABLE, mecanico.get());
//        System.err.println("QUANTIDADE: " + jobs.size());
//        log.debug(jobs.toString());
//        for(JobCandidate job : jobs){
//            log.debug(job.getId().getJobRequest().toString());
//        }
//        Assertions.assertFalse(jobs.isEmpty());
//        Assertions.assertEquals(jobs.size(), 1);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("Deve retornar uma lista de candidaturas para serviços em aberto de uma especialidade de um dado profissional")
//    public void findById_JobRequest_StatusAndAndId_JobRequest_ExpertiseAndId_Professional() {
//        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");
//        Optional<Expertise> mechanic = expertiseRepository.findByName("Mecânico");
//        List<JobCandidate> jobs = jobCandidateRepository.findById_JobRequest_StatusAndAndId_JobRequest_ExpertiseAndId_Professional(JobRequest.Status.AVAILABLE, mechanic.get(), joao);
//        System.err.println("QUANTIDADE: " + jobs.size());
//        log.debug(jobs.toString());
//        for(JobCandidate job : jobs){
//            log.debug(job.getId().getJobRequest().toString());
//        }
//        Assertions.assertFalse(jobs.isEmpty());
//        Assertions.assertEquals(jobs.size(), 1);
//    }
//
//    @AfterEach
//    void tearDown() {
//        jobRequestRepository.deleteAll();
//    }
}