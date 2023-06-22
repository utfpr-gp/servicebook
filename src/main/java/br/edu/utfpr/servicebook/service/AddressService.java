package br.edu.utfpr.servicebook.service;


import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    public void editAddress(Long id, HttpServletRequest request) {
        Optional<User> oUser = this.userService.findById(id);
        User user = oUser.get();
        Optional<City> cities = this.cityService.findById(user.getAddress().getCity().getId());
        City cityUser = cities.get();
//        CityMidDTO cityUser = cityMapper.toMidDto(cities.get());

        Address addressEdit = new Address();
        addressEdit.setNeighborhood(request.getParameter("neighborhood"));
        addressEdit.setStreet(request.getParameter("street"));
        addressEdit.setNumber(request.getParameter("number"));
        addressEdit.setPostalCode(request.getParameter("postalCode").replaceAll("-", ""));
        addressEdit.setCity(cityUser);

        if (!compareAddres(addressEdit, user.getAddress())) {
            user.setAddress(addressEdit);
            this.userService.save(user);
        }
    }

}
