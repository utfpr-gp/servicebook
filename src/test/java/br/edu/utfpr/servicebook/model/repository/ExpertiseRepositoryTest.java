package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.entity.Professional;
import br.edu.utfpr.servicebook.util.CPFUtil;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ExpertiseRepositoryTest {

    public static final Logger log =
            LoggerFactory.getLogger(ExpertiseRepositoryTest.class);

    @Autowired
    ExpertiseRepository expertiseRepository;

    @Autowired
    ProfessionalRepository professionalRepository;

    @BeforeEach
    void setUp() {
        Expertise developerExpertise = new Expertise("Desenvolvedor de Software");
        developerExpertise = expertiseRepository.save(developerExpertise);

        Expertise mechanicExpertise = new Expertise("Mecânico");
        mechanicExpertise = expertiseRepository.save(mechanicExpertise);

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
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar uma lista com UMA especialidade do profissional João")
    public void findByProfessionals() {
        Professional joao = professionalRepository.findByEmailAddress("joao@mail.com");
        List<Expertise> expertises = expertiseRepository.findByProfessionals(joao);
        log.debug(expertises.toString());
        Assertions.assertFalse(expertises.isEmpty());
        Assertions.assertEquals(expertises.size(), 1);
    }

    @AfterEach
    void tearDown() {
        expertiseRepository.deleteAll();
        professionalRepository.deleteAll();
    }
}