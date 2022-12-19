package br.edu.utfpr.servicebook.model.entity;

import br.edu.utfpr.servicebook.security.ProfileEnum;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
@Data
public class Role implements GrantedAuthority, Serializable {

    @Id
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ProfileEnum rolename;

    public Role() {
        // TODO Auto-generated constructor stub
    }

    public Role(String username, ProfileEnum rolename) {
        super();
        this.username = username;
        this.rolename = rolename;
    }

    @Override
    public String getAuthority() {
        return this.getRolename().toString();
    }
}
