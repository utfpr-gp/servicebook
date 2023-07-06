package br.edu.utfpr.servicebook.model.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.servicebook.model.dto.PaymentDTO;
import br.edu.utfpr.servicebook.model.entity.Payment;

@Component
public class PaymentMapper {

    @Autowired
    private ModelMapper mapper;

    public PaymentDTO toDto(Payment entity) {
        PaymentDTO dto = mapper.map(entity, PaymentDTO.class);
        return dto;
    }

    public Payment toEntity(PaymentDTO dto) {
        Payment entity = mapper.map(dto, Payment.class);
        return entity;
    }

}
