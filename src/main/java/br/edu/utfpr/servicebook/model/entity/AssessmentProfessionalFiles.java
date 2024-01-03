package br.edu.utfpr.servicebook.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(name = "assessment_professionals_files")
@Entity
public class AssessmentProfessionalFiles {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assessment_professional_id")
    private AssessmentProfessional assessmentProfessional;

    @Column(nullable = true)
    private String pathImage;

    @Column(nullable = true)
    private String pathVideo;
}
