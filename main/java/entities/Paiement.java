package entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "paiement")
public class Paiement extends AbstractEntity{
    @Column
      private double montant;
    @Column(name = "date")
      private LocalDateTime date;
      @JoinColumn(name = "id_dette")
      @ManyToOne
      private Dette dette;
}
