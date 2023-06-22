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
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(nullable = false)
    private String pathImage;

    @NonNull
    @ManyToOne
    private State state;
}