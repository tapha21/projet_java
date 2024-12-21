package entities;

import lombok.*;

import java.util.List;
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
    @Transient
    private int quantiteSelectionnee; 
    @Transient
    private double PrixTotal;
    @ManyToOne
    private Dette dette;
    private boolean etat;

    // public double getPrixTotal(int quantite) {
    //     return prix * quantite;
    // }


}
