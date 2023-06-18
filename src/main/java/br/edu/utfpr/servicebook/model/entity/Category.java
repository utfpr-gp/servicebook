package br.edu.utfpr.servicebook.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Table(name = "categories")
@NoArgsConstructor
@Entity
public class Category {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String name;

    public Category(String name){
        this.name = name;
    }

//    //  1 categoria x n especialidades

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private Set<Expertise> expertise
            = new HashSet<>();

}
