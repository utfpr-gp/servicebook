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
    @Column
    private Long id;

    @Column
    private String name;

    @NonNull
    @Column
    private Long idState;
}