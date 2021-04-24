package br.edu.utfpr.servicebook.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfessionDTO {
    @NonNull
    private String name;
}
