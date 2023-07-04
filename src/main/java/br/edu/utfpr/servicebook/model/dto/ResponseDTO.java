package br.edu.utfpr.servicebook.model.dto;

import lombok.Data;

@Data
public class ResponseDTO {
    private String message;
    private Object data;
}
