package com.entities;

import org.hibernate.annotations.ColumnDefault;

import lombok.*;
import javax.persistence.*;

@Data
@Entity

@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "selectByRole", query = "SELECT u FROM User u WHERE u.role = :role"),
    @NamedQuery(name = "selectByID", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "selectByLogin", query = "SELECT u FROM User u WHERE u.login = :login")
})   

public class User extends AbstractEntity{

    
    @Column(length = 50, unique = true)
    private String login;

    @Column(length = 50)
    private String prenom;

    @Column(length = 50)
    private String nom;

    @Column(length = 255, unique = true)
    private String password;

    @OneToOne
    @JoinColumn(name = "clients_id", nullable = true)
    private Client client;
    @OneToOne
    @JoinColumn(name = "roles_id", nullable = true)
    private Role role;
    @ColumnDefault(value = "true")
    private boolean etat;

    public User() {

    }
    public String getRoleName() {
        return role != null ? role.getNom() : "Aucun rôle";
    }

}