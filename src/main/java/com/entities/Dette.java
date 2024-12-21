package com.entities;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "dette")
@EqualsAndHashCode(callSuper = false)
public class Dette extends AbstractEntity {

    private LocalDateTime date;

    @Transient
    private double montantRestant;

    private double montant;

    private double montantVerser;

    private boolean reglee;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @ToString.Exclude // Exclusion pour éviter la boucle infinie
    private Client client;

    @ManyToMany(mappedBy = "dette")
    private List<Detail> details;

    @OneToMany(mappedBy = "dette")
    private List<Article> articles;

    @OneToMany(mappedBy = "dette")
    private List<Paiement> paiements;

    public boolean estReglee() {
        return reglee;
    }

    public String getClientSurname() {
        return client != null ? client.getSurname() : "";
    }
}