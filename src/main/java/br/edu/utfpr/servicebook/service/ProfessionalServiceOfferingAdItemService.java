package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.ProfessionalServicePackageOffering;
import br.edu.utfpr.servicebook.model.entity.ProfessionalServiceOfferingAdItem;
import br.edu.utfpr.servicebook.model.repository.ProfessionalServiceOfferingAdItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ProfessionalServiceOfferingAdItemService {

    @Autowired
    private ProfessionalServiceOfferingAdItemRepository professionalServiceOfferingAdItemRepository;

    public List<ProfessionalServiceOfferingAdItem> findAllByProfessionalServicePackageOffering(ProfessionalServicePackageOffering professionalServicePackageOffering){
        return professionalServiceOfferingAdItemRepository.findAllByProfessionalServicePackageOffering(professionalServicePackageOffering);
    }
}
