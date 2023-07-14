package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.ProfessionalServiceOfferingAd;
import br.edu.utfpr.servicebook.model.entity.ProfessionalServiceOfferingAdItem;
import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.model.repository.ProfessionalServiceOfferingAdItemRepository;
import br.edu.utfpr.servicebook.model.repository.ProfessionalServiceOfferingAdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@org.springframework.stereotype.Service
public class ProfessionalServiceOfferingAdItemService {

    @Autowired
    private ProfessionalServiceOfferingAdItemRepository professionalServiceOfferingAdItemRepository;

    public List<ProfessionalServiceOfferingAdItem> findAllByProfessionalServiceOfferingAd(ProfessionalServiceOfferingAd professionalServiceOfferingAd){
        return professionalServiceOfferingAdItemRepository.findAllByProfessionalServiceOfferingAd(professionalServiceOfferingAd);
    }
}
