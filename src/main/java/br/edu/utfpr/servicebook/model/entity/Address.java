package br.edu.utfpr.servicebook.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "addresses")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String street;

    private String number;

    @NonNull
    @Column(name = "postal_code")
    private String postalCode;

    private String neighborhood;

    @NonNull
    @ManyToOne
    private City city;
}