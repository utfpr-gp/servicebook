package br.edu.utfpr.servicebook.model.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cities")
@Data
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @Column
    private Long idState;
}