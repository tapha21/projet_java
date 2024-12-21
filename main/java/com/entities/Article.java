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
    private boolean etat;
    private double montant;


    // public double getPrixTotal(int quantite) {
    //     return prix * quantite;
    // }
   public Article() {
    }
    public Article(String libelle, double prix, int quantityInStock) {
        this.libelle = libelle;
        this.prix = prix;
        this.quantityInStock = quantityInStock;
        this.montant = prix * quantityInStock;
    }
}
