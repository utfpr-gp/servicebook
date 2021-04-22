package br.edu.utfpr.servicebook.model.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "states")
@Data
@NoArgsConstructor
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column
    private Long id;

    @NonNull
    @Column(unique = true)
    private String name;

    @NonNull
    @Column
    private String uf;

}