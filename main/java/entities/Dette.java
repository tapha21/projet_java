package entities;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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

    public String toString() {
      return "Dette{" +
              "id=" + getId() +
              ", date=" + date +
              ", montant=" + montant +
              ", montantVerser=" + montantVerser +
              ", montantRestant=" + montantRestant +
              ", reglee=" + reglee +
              ", client=" + (client != null ? client.getSurname() : "null") +
              '}';
  }
}
