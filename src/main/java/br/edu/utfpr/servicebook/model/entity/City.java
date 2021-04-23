package br.edu.utfpr.servicebook.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cities")
@Data
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private Long idState;
}