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
import java.util.Date;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class JobContractedRepositoryTest {

    public static final Logger log = LoggerFactory.getLogger(JobContractedRepositoryTest.class);

    @Autowired
    IndividualRepository individualRepository;

    @Autowired
    ExpertiseRepository expertiseRepository;

    @Autowired
    ProfessionalExpertiseRepository professionalExpertiseRepository;

    @Autowired
    JobRequestRepository jobRequestRepository;

    @Autowired
    JobCandidateRepository jobCandidateRepository;

    @Autowired
    JobContractedRepository jobContractedRepository;

    final Date dateOfNow = new Date();

    @BeforeEach
    void setUp() {
        Individual individual = new Individual("João da Silva", "professional@mail.com", "Senha123","(42) 88999-9967", CPFUtil.geraCPF());
        individual = individualRepository.save(individual);

        Expertise expertise = new Expertise("Expertise");
        expertise = expertiseRepository.save(expertise);

        ProfessionalExpertise professionalExpertise = new ProfessionalExpertise(individual, expertise);
        professionalExpertiseRepository.save(professionalExpertise);

        JobRequest jobRequest = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, dateOfNow);
        jobRequest.setExpertise(expertise);
        jobRequestRepository.save(jobRequest);

        JobCandidate jobCandidate = new JobCandidate(jobRequest, individual);
        jobCandidate.setChosenByBudget(true);
        jobCandidateRepository.save(jobCandidate);

        JobContracted jobContracted = new JobContracted(jobRequest, individual);
        jobContracted.setRating(5);
        jobContracted.setComments("Comments");

        jobContracted = jobContractedRepository.save(jobContracted);

        jobRequest.setJobContracted(jobContracted);
        jobRequestRepository.save(jobRequest);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar o total de trabalhos contratados de um profissional")
    public void countByProfessional() {
        Optional<Individual> individual = individualRepository.findByEmail("professional@mail.com");
        System.out.println("Prof" + individual);

        Optional<Long> jobs = jobContractedRepository.countByIndividual(individual.get());

        Assertions.assertEquals(jobs.get(), 1);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar o total de avaliações dos trabalhos contratados de um profissional")
    public void countRatingByProfessional() {
        Optional<Individual> individual = individualRepository.findByEmail("professional@mail.com");
        Optional<Long> ratings = jobContractedRepository.countRatingByIndividual(individual.get());

        Assertions.assertFalse(ratings.get() == 0);
        Assertions.assertEquals(ratings.get(), 1);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar o total de comentários dos trabalhos contratados de um profissional")
    public void countCommentsByProfessional() {
        Optional<Individual> individual = individualRepository.findByEmail("professional@mail.com");
        Optional<Long> comments = jobContractedRepository.countCommentsByIndividual(individual.get());

        Assertions.assertFalse(comments.get() == 0);
        Assertions.assertEquals(comments.get(), 1);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar o total de trabalhos contratados de um profissional para uma dada especialidade")
    public void countByProfessionalAndJobRequest_Expertise() {
        Optional<Individual> individual = individualRepository.findByEmail("professional@mail.com");
        Optional<Expertise> expertise = expertiseRepository.findByName("Expertise");

        Optional<Long> jobs = jobContractedRepository.countByIndividualAndJobRequest_Expertise(individual.get(), expertise.get());

        Assertions.assertFalse(jobs.get() == 0);
        Assertions.assertEquals(jobs.get(), 1);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar o total de avaliações dos trabalhos contratados de um profissional para uma dada especialidade")
    public void countRatingByProfessionalAndJobRequest_Expertise() {
        Optional<Individual> individual = individualRepository.findByEmail("professional@mail.com");
        Optional<Expertise> expertise = expertiseRepository.findByName("Expertise");

        Optional<Long> ratings = jobContractedRepository.countRatingByIndividualAndJobRequest_Expertise(individual.get(), expertise.get());

        Assertions.assertFalse(ratings.get() == 0);
        Assertions.assertEquals(ratings.get(), 1);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar o total de comentários dos trabalhos contratados de um profissional para uma dada especialidade")
    public void countCommentsByProfessionalAndJobRequest_Expertise() {
        Optional<Individual> individual = individualRepository.findByEmail("professional@mail.com");
        Optional<Expertise> expertise = expertiseRepository.findByName("Expertise");

        Optional<Long> comments = jobContractedRepository.countCommentsByIndividualAndJobRequest_Expertise(individual.get(), expertise.get());

        Assertions.assertFalse(comments.get() == 0);
        Assertions.assertEquals(comments.get(), 1);
    }

    @AfterEach
    void tearDown() {

    }

}
