package com.entities;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data

@Table(name = "detail")
@EqualsAndHashCode(callSuper = false)
public class Detail extends AbstractEntity {

    private double prixVente;

    private double qteVente;

    private int quantite; // Corrigé en 'quantite' pour la cohérence

    private int prixTotal;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false) // Assurez-vous d'avoir la bonne clé étrangère
    private Article article;

    @ManyToOne
    @JoinColumn(name = "dette_id", nullable = false) // Assurez-vous d'avoir la bonne clé étrangère
    private Dette dette;
}
