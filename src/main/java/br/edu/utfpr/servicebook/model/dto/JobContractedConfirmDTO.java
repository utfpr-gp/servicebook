package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobContractedConfirmDTO {

    /**
     * Data prevista para realizar o serviço
     */
    private String todoDate;

    /**
     * O profissional confirma ou não que realizará o serviço para o qual foi contratado.
     */
    private boolean confirm;

}
