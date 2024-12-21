package com.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "client")
@EqualsAndHashCode(callSuper = false)
public class Client extends AbstractEntity {

    @Column(length = 50, unique = true)
    private String nom;
    @Column(length = 50, unique = true)
    private String prenom;

    @Column(length = 12, unique = true)
    private String telephone;

    @Column(length = 50)
    private String adresse;

    @Column(length = 50)
    private String ville;
    @Column(length = 50)
    private String quartier; 
     @Column(length = 50)
    private String numerovilla;
    public Client() {
    }
}