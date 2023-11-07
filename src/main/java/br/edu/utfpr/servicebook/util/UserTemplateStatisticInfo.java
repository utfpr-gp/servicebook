package br.edu.utfpr.servicebook.util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTemplateStatisticInfo {
    private Long jobs;
    private Long comments;
    private Long ratings;
    private Integer ratingScore;
}