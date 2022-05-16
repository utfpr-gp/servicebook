package br.edu.utfpr.servicebook.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor

@Table(name = "states")
@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String name;

    @NonNull
    @Column(unique = true)
    private String uf;

}