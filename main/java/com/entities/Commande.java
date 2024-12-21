package com.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "commande")
@EqualsAndHashCode(callSuper = false)
public class Commande extends AbstractEntity {

    @Column(length = 50)
    private String numeroCommande;

    @Column(length = 50)
    private Date dateCommande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles;

    public Commande() {
    }

    public Commande(String numeroCommande, Date dateCommande, Client client) {
        this.numeroCommande = numeroCommande;
        this.dateCommande = dateCommande;
        this.client = client;
    }
}