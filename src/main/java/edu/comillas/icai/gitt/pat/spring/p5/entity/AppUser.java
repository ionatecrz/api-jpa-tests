package edu.comillas.icai.gitt.pat.spring.p5.entity;

import edu.comillas.icai.gitt.pat.spring.p5.model.Role;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO#2
 * Completa la entidad AppUser (cuya tabla en BD se llamará APP_USER)
 * para que tenga los siguientes campos obligatorios:
 * - id, que será la clave primaria numérica y autogenerada
 * - email, que debe tener valores únicos en toda la tabla
 * - password
 * - role, modelado con la clase Role ya existente en el proyecto
 * - name
 */

@Entity
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false,unique = true) String email;
    @Column(nullable = false) String password;
    @Column(nullable = false) Role role;
    @Column(nullable = false) String name;
    // Añadido por el deleteCascade
    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Token token;




    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
        token.setAppUser(this);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

