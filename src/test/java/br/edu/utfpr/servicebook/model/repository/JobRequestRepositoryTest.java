package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
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

    public static final Logger log =
            LoggerFactory.getLogger(JobRequestRepositoryTest.class);

    @BeforeEach
    void setUp() {
        Expertise e1 = new Expertise("Desenvolvedor de Software");
        Expertise e2 = new Expertise("Mecânico");
        e1 = expertiseRepository.save(e1);
        e2 = expertiseRepository.save(e2);

        JobRequest jb1 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, DateUtil.getNextWeek());
        jb1.setExpertise(e1);
        JobRequest jb2 = new JobRequest(JobRequest.Status.AVAILABLE, "", 10, DateUtil.getNextWeek());
        jb1.setExpertise(e2);
        JobRequest jb3 = new JobRequest(JobRequest.Status.CLOSED, "", 10, DateUtil.getNextWeek());
        jb1.setExpertise(e1);
        JobRequest jb4 = new JobRequest(JobRequest.Status.CLOSED, "", 10, DateUtil.getNextWeek());
        jb1.setExpertise(e2);

        jobRequestRepository.save(jb1);
        jobRequestRepository.save(jb2);
        jobRequestRepository.save(jb3);
        jobRequestRepository.save(jb4);

    }

    @Test
    @Transactional
    @DisplayName("Deve retornar uma lista de requisições disponíveis para uma especialidade")
    public void findByStatusAvailableAndExpertise() {
        Optional<Expertise> mecanico = expertiseRepository.findByName("Mecânico");
        List<JobRequest> jobs = jobRequestRepository.findByStatusAndExpertise(JobRequest.Status.AVAILABLE, mecanico.get());
        log.debug(jobs.toString());
        Assertions.assertFalse(jobs.isEmpty());
        Assertions.assertEquals(jobs.size(), 1);
    }

    @AfterEach
    void tearDown() {
        jobRequestRepository.deleteAll();
    }
}