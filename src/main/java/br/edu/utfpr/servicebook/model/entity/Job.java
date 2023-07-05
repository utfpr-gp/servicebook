package br.edu.utfpr.servicebook.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "job")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Entity
public class Job implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @ManyToOne
    @JoinColumn(name = "expertise_id")
    private Expertise expertise;

    @NonNull
    private String description;


    private Long salary;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;


}
