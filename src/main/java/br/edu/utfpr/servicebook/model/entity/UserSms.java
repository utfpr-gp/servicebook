package br.edu.utfpr.servicebook.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "users_sms")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class UserSms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String phoneNumber;

    @NonNull
    @Column(unique = true)
    private String code;

}
