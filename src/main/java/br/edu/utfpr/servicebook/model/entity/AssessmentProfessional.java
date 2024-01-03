package br.edu.utfpr.servicebook.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.entity.User;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Table(name = "assessment_professionals")
@Entity
public class AssessmentProfessional {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float punctuality;

    private float quality;

    private String comment;

    private LocalDate date;

    @PrePersist
    public void onPersist() {
        this.date = LocalDate.now();
    }

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private User professional;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "jobRequest_id")
    private JobRequest jobRequest;

}
