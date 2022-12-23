package br.edu.utfpr.servicebook.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.ModelAndView;

import br.edu.utfpr.servicebook.util.sidePanel.SidePanelStatisticsDTO;

import javax.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.Optional;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProfessionalHomeControllerTest {
    public static final Logger log =
            LoggerFactory.getLogger(ProfessionalHomeControllerTest.class);
    @Autowired
    private ProfessionalHomeController controller;

    @Test
    @DisplayName("Deve detectar a propriedade 'dataIndividual.ratingScore' no ModelAndView retornado")
    public void professionalHomeViewHasRatingByExpertiseProperty() {
        try {
            Long expertiseId = 2L;
            ModelAndView mv = this.controller.showMyAccountProfessional(Optional.of(expertiseId));
            Map<String, Object> modelObjects = mv.getModel();

            Assertions.assertTrue(modelObjects.containsKey("dataIndividual"));
            Object dataIndividual = modelObjects.get("dataIndividual");
            SidePanelStatisticsDTO sidePanelItems = (SidePanelStatisticsDTO) dataIndividual;
            Assertions.assertEquals("Integer", sidePanelItems.getRatingScore().getClass().getSimpleName());
        } catch (Exception e) {
            log.debug("TEST ERROR:" + e.getMessage());
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Deve assegurar que método 'showMyAccountProfessional' lança exceção caso 'expertise.id' < 0")
    public void showMyAccountProfessionalThrowsExceptionOnCallWithLessThanZeroId() {
       Throwable exception = Assertions.assertThrows(Exception.class, () -> {
            this.controller.showMyAccountProfessional(Optional.of(-2L));
        });

        Assertions.assertEquals(
                "O identificador da especialidade não pode ser negativo. Por favor, tente novamente.",
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("Deve assegurar que método 'showMyAccountProfessional' lança exceção caso id aponte para entidade não existente")
    public void showMyAccountProfessionalThrowsExceptionOnCallWithNonExistingExpertiseId() {
        Long nonExistingExpertiseId = 500L;

        Throwable exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            this.controller.showMyAccountProfessional(Optional.of(nonExistingExpertiseId));
        });

        Assertions.assertEquals(
                "A especialidade não foi encontrada pelo id informado. Por favor, tente novamente.",
                exception.getMessage()
        );
    }
}
