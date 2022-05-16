package br.edu.utfpr.servicebook.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "users_code")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class UserCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String email;

    @NonNull
    @Column(unique = true)
    private String code;

}
