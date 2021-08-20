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
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class JobContractedRepositoryTest {

    public static final Logger log = LoggerFactory.getLogger(JobContractedRepositoryTest.class);

    @Autowired
    ProfessionalRepository professionalRepository;

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
        Professional professional = new Professional("Professional", "professional@mail.com", "", "", CPFUtil.geraCPF());
        professional = professionalRepository.save(professional);

        Expertise expertise = new Expertise("Expertise");
        expertise = expertiseRepository.save(expertise);

        ProfessionalExpertise professionalExpertise = new ProfessionalExpertise(professional, expertise);
        professionalExpertiseRepository.save(professionalExpertise);

        JobRequest jobRequest = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, dateOfNow);
        jobRequest.setExpertise(expertise);
        jobRequestRepository.save(jobRequest);

        JobCandidate jobCandidate = new JobCandidate(jobRequest, professional);
        jobCandidate.setChosenByBudget(true);
        jobCandidateRepository.save(jobCandidate);

        JobContracted jobContracted = new JobContracted(jobRequest, professional);
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
        Professional professional = professionalRepository.findByEmailAddress("professional@mail.com");
        Optional<Long> jobs = jobContractedRepository.countByProfessional(professional);

        Assertions.assertFalse(jobs.get() == 0);
        Assertions.assertEquals(jobs.get(), 1);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar o total de avaliações dos trabalhos contratados de um profissional")
    public void countRatingByProfessional() {
        Professional professional = professionalRepository.findByEmailAddress("professional@mail.com");
        Optional<Long> ratings = jobContractedRepository.countRatingByProfessional(professional);

        Assertions.assertFalse(ratings.get() == 0);
        Assertions.assertEquals(ratings.get(), 1);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar o total de comentários dos trabalhos contratados de um profissional")
    public void countCommentsByProfessional() {
        Professional professional = professionalRepository.findByEmailAddress("professional@mail.com");
        Optional<Long> comments = jobContractedRepository.countCommentsByProfessional(professional);

        Assertions.assertFalse(comments.get() == 0);
        Assertions.assertEquals(comments.get(), 1);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar o total de trabalhos contratados de um profissional para uma dada especialidade")
    public void countByProfessionalAndJobRequest_Expertise() {
        Professional professional = professionalRepository.findByEmailAddress("professional@mail.com");
        Optional<Expertise> expertise = expertiseRepository.findByName("Expertise");

        Optional<Long> jobs = jobContractedRepository.countByProfessionalAndJobRequest_Expertise(professional, expertise.get());

        Assertions.assertFalse(jobs.get() == 0);
        Assertions.assertEquals(jobs.get(), 1);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar o total de avaliações dos trabalhos contratados de um profissional para uma dada especialidade")
    public void countRatingByProfessionalAndJobRequest_Expertise() {
        Professional professional = professionalRepository.findByEmailAddress("professional@mail.com");
        Optional<Expertise> expertise = expertiseRepository.findByName("Expertise");

        Optional<Long> ratings = jobContractedRepository.countRatingByProfessionalAndJobRequest_Expertise(professional, expertise.get());

        Assertions.assertFalse(ratings.get() == 0);
        Assertions.assertEquals(ratings.get(), 1);
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar o total de comentários dos trabalhos contratados de um profissional para uma dada especialidade")
    public void countCommentsByProfessionalAndJobRequest_Expertise() {
        Professional professional = professionalRepository.findByEmailAddress("professional@mail.com");
        Optional<Expertise> expertise = expertiseRepository.findByName("Expertise");

        Optional<Long> comments = jobContractedRepository.countCommentsByProfessionalAndJobRequest_Expertise(professional, expertise.get());

        Assertions.assertFalse(comments.get() == 0);
        Assertions.assertEquals(comments.get(), 1);
    }

    @AfterEach
    void tearDown() {

    }

}
