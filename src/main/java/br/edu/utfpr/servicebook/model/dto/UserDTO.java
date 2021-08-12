package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private String type;

    private String gender;

    private String profilePicture;

    private Date birthDate;

    private String phoneNumber;

    private boolean phoneVerified;

    private boolean emailVerified;

    private boolean identityVerified;
}