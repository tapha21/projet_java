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
    private String surname;

    @Column(length = 12, unique = true)
    private String telephone;

    @Column(length = 50)
    private String adresse;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id", nullable = true)
    private User users;

    @OneToMany(mappedBy = "client")
    @ToString.Exclude // Exclusion pour éviter la boucle infinie
    private List<Dette> dettes;

    public Client() {
    }
}