package br.edu.utfpr.servicebook.model.entity;

import javax.persistence.*;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "job_request")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class JobRequest {

    @Id

    @NonNull
    private Long category_id;

    @Column(name = "date_created", nullable = false, updatable = false)
    @CreatedDate
    private LocalDate createdDate;

    @NonNull
    private Date date_expired;

    @NonNull
    private String status;

    @NonNull
    private String description;

    @NonNull
    private Integer quantity_candidators_max;

    @NonNull
    private LocalDate request_expiration;

    @NonNull
    private Boolean client_confirmation;

    @NonNull
    private Boolean professional_confirmation = false;


}