package br.edu.utfpr.servicebook.model.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Data
@Table(name = "job_requests")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class JobRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "expertise_id")
    private Expertise expertise;

    @NonNull
    private String status;

    @NonNull
    private String description;

    @NonNull
    private Integer quantityCandidatorsMax;

    @NonNull
    private Date dateProximity;

    @Column(name = "date_created", nullable = false, updatable = false)
    @CreatedDate
    private LocalDate dateCreated;

    @NonNull
    private LocalDate dateExpired;

    @NonNull
    private Boolean client_confirmation;

    @NonNull
    private boolean professionalConfirmation = false;

    @OneToMany(mappedBy = "jobRequest")
    private Set<JobImages> jobImages = new HashSet<>();

    @OneToOne(mappedBy = "jobRequest")
    private JobContracted jobContracted;

}