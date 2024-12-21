package com.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "article")
@EqualsAndHashCode(callSuper = false, of = {"reference", "libelle"})
public class Article extends AbstractEntity {
    
    @Column(length = 50)
    private String libelle;
    @Column(length = 50)
    private String reference;
    private double prix;
    private int quantityInStock;
    private Integer PrixTotal;
    @ManyToOne
    private Dette dette;
    private boolean etat;

    // public double getPrixTotal(int quantite) {
    //     return prix * quantite;
    // }
   public Article() {
    }
    public Article(String reference, String libelle, int quantityInStock, double prix) {
        this.reference = reference;
        this.libelle = libelle;
        this.quantityInStock = quantityInStock;
        this.prix = prix;
    }
}
