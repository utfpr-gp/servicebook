package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.util.CPFUtil;
import br.edu.utfpr.servicebook.util.DateUtil;
import org.h2.tools.Server;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.sql.SQLException;
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

    @Autowired
    ProfessionalExpertiseRepository professionalExpertiseRepository;

    @Autowired
    JobContractedRepository jobContractedRepository;

    public static final Logger log =
            LoggerFactory.getLogger(JobRequestRepositoryTest.class);

    final Date dateOfNow = new Date();

    private static final String MECHANIC = "Mecânico de moto";
    private static final String DEVELOPER = "Desenvolvedor de Sistemas";
    @BeforeEach
    void setUp() {
        Expertise developerExpertise = new Expertise(DEVELOPER);
        developerExpertise = expertiseRepository.save(developerExpertise);

        Expertise mechanicExpertise = new Expertise(MECHANIC);
        mechanicExpertise = expertiseRepository.save(mechanicExpertise);

        JobRequest jb1 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, dateOfNow);
        jb1.setExpertise(developerExpertise);

        JobRequest jb2 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, dateOfNow);
        jb2.setExpertise(mechanicExpertise);

        //ninguém se candidatou a este
        JobRequest jb3 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, dateOfNow);
        jb3.setExpertise(mechanicExpertise);

        JobRequest jb4 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, dateOfNow);
        jb4.setExpertise(mechanicExpertise);

        JobRequest jb5 = new JobRequest(JobRequest.Status.CLOSED, "", 10, dateOfNow);
        jb5.setExpertise(mechanicExpertise);

        jobRequestRepository.save(jb1);
        jobRequestRepository.save(jb2);
        jobRequestRepository.save(jb3);
        jobRequestRepository.save(jb4);
        jobRequestRepository.save(jb5);

        //João Mecânico e Desenvolvedor
        Professional joao = new Professional("Roberto Carlos", "joao@mail.com", "(42) 88999-9991", CPFUtil.geraCPF());
        joao = professionalRepository.save(joao);

        ProfessionalExpertise professionalExpertise1 = new ProfessionalExpertise(joao, mechanicExpertise);
        professionalExpertiseRepository.save(professionalExpertise1);

        ProfessionalExpertise professionalExpertise2 = new ProfessionalExpertise(joao, developerExpertise);
        professionalExpertiseRepository.save(professionalExpertise2);

        //Maria Desenvolvedora
        Professional maria = new Professional("Maria", "maria@mail.com", "(42) 88999-9992", CPFUtil.geraCPF());
        maria = professionalRepository.save(maria);

        ProfessionalExpertise professionalExpertise3 = new ProfessionalExpertise(maria, developerExpertise);
        professionalExpertiseRepository.save(professionalExpertise3);

        JobCandidate candidate1 = new JobCandidate(jb2, joao);
        jobCandidateRepository.save(candidate1);

        //joão foi escolhido para orçamento para o jb2
        JobCandidate candidate2 = new JobCandidate(jb4, joao);
        candidate2.setChosenByBudget(true);
        jobCandidateRepository.save(candidate2);

        //maria se candidatou a desenvolvedora
        JobCandidate candidate3 = new JobCandidate(jb1, maria);
        jobCandidateRepository.save(candidate3);

        //joão foi candidato a um Job que está CLOSED e foi ele o contratado
        JobCandidate candidate5 = new JobCandidate(jb5, joao);
        jobCandidateRepository.save(candidate5);

        JobContracted jobContracted1 = new JobContracted(jb5, joao);
        jobContracted1 = jobContractedRepository.save(jobContracted1);
        jb5.setJobContracted(jobContracted1);
        jobRequestRepository.save(jb5);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar uma lista de requisições disponíveis para uma especialidade")
    public void findByStatusAndExpertise() {
        Optional<Expertise> mecanico = expertiseRepository.findByName(MECHANIC);
        List<JobRequest> jobs = jobRequestRepository.findByStatusAndExpertise(JobRequest.Status.AVAILABLE, mecanico.get());
        log.debug(jobs.toString());
        Assertions.assertFalse(jobs.isEmpty());
        Assertions.assertEquals(jobs.size(), 4);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar uma lista de requisições disponíveis para uma especialidade que um profissional se candidatou")
    public void findByStatusAndExpertiseAndJobCandidates_Professional() {
        Optional<Expertise> mecanico = expertiseRepository.findByName(MECHANIC);
        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");
        List<JobRequest> jobs = jobRequestRepository.findByStatusAndExpertiseAndJobCandidates_Professional(JobRequest.Status.AVAILABLE, mecanico.get(), joao);
        log.debug(jobs.toString());
        Assertions.assertFalse(jobs.isEmpty());
        Assertions.assertEquals(jobs.size(), 3);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar uma lista de requisições disponíveis para uma especialidade, mas apenas aquelas que não tiveram candidaturas")
    public void findByStatusAndExpertiseAndJobCandidatesIsNull() {
        Optional<Expertise> mecanico = expertiseRepository.findByName(MECHANIC);
        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");
        List<JobRequest> jobs = jobRequestRepository.findByStatusAndExpertiseAndJobCandidatesIsNull(JobRequest.Status.AVAILABLE, mecanico.get());
        log.debug(jobs.toString());
        Assertions.assertFalse(jobs.isEmpty());
        Assertions.assertEquals(jobs.size(), 1);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar uma lista de requisições disponíveis para uma especialidade, mas apenas aquelas que um certo profissional ainda não se candidatou")
    public void findByStatusAndExpertiseAndJobCandidates_ProfessionalNot() {
        Optional<Expertise> mecanico = expertiseRepository.findByName(DEVELOPER);
        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");
        List<JobRequest> jobs = jobRequestRepository.findByStatusAndExpertiseAndJobCandidates_ProfessionalNot(JobRequest.Status.AVAILABLE, mecanico.get(), joao);
        log.debug(jobs.toString());
        Assertions.assertFalse(jobs.isEmpty());
        Assertions.assertEquals(jobs.size(), 1);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar uma lista de requisições disponíveis e que ainda não tem candidatos, mas apenas aquelas que um certo profissional ainda não se candidatou")
    public void findByStatusAndJobCandidates_ProfessionalNot() {
        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");
        List<JobRequest> jobs = jobRequestRepository.findByStatusAndJobCandidates_ProfessionalNot(JobRequest.Status.AVAILABLE, joao);
        log.debug(jobs.toString());
        Assertions.assertFalse(jobs.isEmpty());
        Assertions.assertEquals(jobs.size(), 2);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar uma lista de requisições disponíveis para uma especialidade, mas apenas aquelas que um certo profissional ainda não se candidatou")
    public void findByStatusAndExpertiseAndJobCandidatesIsNullOrJobCandidates_ProfessionalNot() {
        Optional<Expertise> mecanico = expertiseRepository.findByName(DEVELOPER);
        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");
        List<JobRequest> jobs = jobRequestRepository.findByStatusAndExpertiseAndJobCandidatesIsNullOrJobCandidates_ProfessionalNot(JobRequest.Status.AVAILABLE, mecanico.get(), joao);
        log.debug(jobs.toString());
        Assertions.assertFalse(jobs.isEmpty());
        Assertions.assertEquals(jobs.size(), 2);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar uma lista de requisições disponíveis, aquelas que ainda não receberam candidaturas e aquelas que um certo profissional ainda não se candidatou")
    public void findByStatusAndJobCandidatesIsNullOrJobCandidates_ProfessionalNot() {
        Optional<Expertise> mecanico = expertiseRepository.findByName(DEVELOPER);
        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");
        List<JobRequest> jobs = jobRequestRepository.findByStatusAndJobCandidatesIsNullOrJobCandidates_ProfessionalNot(JobRequest.Status.AVAILABLE, joao);
        log.debug(jobs.toString());
        Assertions.assertFalse(jobs.isEmpty());
        Assertions.assertEquals(jobs.size(), 3);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar uma lista de requisições disponíveis, aquelas que ainda não receberam candidaturas e aquelas que um certo profissional ainda não se candidatou")
    public void findByStatusAndJobCandidatesIsNullOrJobCandidates_ProfessionalNotPageable() {
        Optional<Expertise> mecanico = expertiseRepository.findByName(DEVELOPER);
        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<JobRequest> jobs = jobRequestRepository.findByStatusAndJobCandidatesIsNullOrJobCandidates_ProfessionalNot(JobRequest.Status.AVAILABLE, joao, pageRequest);
        log.debug(jobs.toString());
        Assertions.assertFalse(jobs.isEmpty());
        Assertions.assertEquals(jobs.getTotalElements(), 3);
    }






//    @Test
//    @Transactional
//    @DisplayName("Deve retornar uma lista de requisições no estado de CLOSED e para uma especialidade de MECÂNICO, " +
//            "mas apenas aquelas que " +
//            "o profissional João foi contratado para realizar. ")
//    public void findByStatusAndExpertiseAndJobContracted_Professional() {
//        Optional<Expertise> mechanic = expertiseRepository.findByName("Mecânico");
//        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");
//        List<JobRequest> jobs = jobRequestRepository.findByStatusAndExpertiseAndJobContracted_Professional(JobRequest.Status.CLOSED, mechanic.get(), joao);
//        log.debug(jobs.toString());
//        Assertions.assertFalse(jobs.isEmpty());
//        Assertions.assertEquals(jobs.size(), 1);
//    }


    @AfterEach
    void tearDown() {
        jobRequestRepository.deleteAll();
    }
}