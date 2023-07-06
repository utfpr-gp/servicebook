package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PaymentDTO {
    
    @NotEmpty(message = "O identificador do pagamento é obrigatório")
    private Integer paymentId;

    @NotEmpty(message = "O status do pagamento é obrigatório")
    private String status;
}
