package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.util.CPFUtil;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ExpertiseRepositoryTest {

    public static final Logger log =
            LoggerFactory.getLogger(ExpertiseRepositoryTest.class);

    @Autowired
    ExpertiseRepository expertiseRepository;

    @Autowired
    IndividualRepository individualRepository;

    @Autowired
    ProfessionalExpertiseRepository professionalExpertiseRepository;

    @BeforeEach
    void setUp() {
        Expertise developerExpertise = new Expertise("Desenvolvedor de Software");
        developerExpertise = expertiseRepository.save(developerExpertise);

        Expertise mechanicExpertise = new Expertise("Mecânico");
        mechanicExpertise = expertiseRepository.save(mechanicExpertise);

        //João Mecânico
        Individual joao = new Individual("Roberto Carlos", "joao@mail.com", "Senha123", "(42) 88999-9992", CPFUtil.geraCPF());
        joao = individualRepository.save(joao);

        ProfessionalExpertise professionalExpertise1 = new ProfessionalExpertise(joao, mechanicExpertise);
        professionalExpertiseRepository.save(professionalExpertise1);

        //Maria Desenvolvedora
        Individual maria = new Individual("Maria", "maria@mail.com", "Senha123", "(42) 88999-9993", CPFUtil.geraCPF());
        maria = individualRepository.save(maria);

        ProfessionalExpertise professionalExpertise2 = new ProfessionalExpertise(maria, developerExpertise);
        professionalExpertiseRepository.save(professionalExpertise2);
    }



    @AfterEach
    void tearDown() {
        expertiseRepository.deleteAll();
        individualRepository.deleteAll();
    }
}