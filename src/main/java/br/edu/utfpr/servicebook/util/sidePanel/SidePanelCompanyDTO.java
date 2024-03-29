package br.edu.utfpr.servicebook.util.sidePanel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SidePanelCompanyDTO {
    private Long jobs;
    private Long comments;
    private Long ratings;
    private Integer ratingScore;
}