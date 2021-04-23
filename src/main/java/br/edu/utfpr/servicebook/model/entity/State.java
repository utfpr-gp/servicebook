package br.edu.utfpr.servicebook.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "states")
@Data
@NoArgsConstructor
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private String uf;

}