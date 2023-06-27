package br.edu.utfpr.servicebook.service;


import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.CityMapper;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.model.mapper.UserMapper;
import br.edu.utfpr.servicebook.util.UserWizardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    public Boolean compareAddres(Address address, Address addressUser) {
        if (address.getNumber().equals(addressUser.getNumber())
            && address.getPostalCode().equals(addressUser.getPostalCode())
                && address.getStreet().equals(addressUser.getStreet()) &&
                address.getNeighborhood().equals(addressUser.getNeighborhood())
                && address.getCity().getName().equals(addressUser.getCity().getName())) {
                return true;
        } else {
            return false;
        }
    }

    public void editAddress(Long id, AddressDTO dto, BindingResult errors, HttpSession httpSession) {
        try {
            Optional<City> oCity = cityService.findByName(dto.getCity());

            if (!oCity.isPresent()) {
                errors.rejectValue("city", "error.dto", "Cidade não cadastrada! Por favor, insira uma cidade cadastrada.");
            }

            City cityMidDTO = oCity.get();

            Address addressFullDTO = new Address();
            Optional<User> oUser = this.userService.findById(id);
            User user = oUser.get();
            user.getAddress().setStreet(dto.getStreet().trim());
            user.getAddress().setNumber(dto.getNumber().trim());
            user.getAddress().setPostalCode(dto.getPostalCode().trim());
            user.getAddress().setNeighborhood(dto.getNeighborhood().trim());
            user.getAddress().setCity(cityMidDTO);
            this.userService.save(user);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

    }

}
