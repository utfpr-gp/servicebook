package br.edu.utfpr.servicebook.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Table(name = "cities")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    @ManyToOne
    private State state;
}