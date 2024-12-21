package core.RepoInter;

import java.util.List;

import core.config.Repository;
import entities.Paiement;

public interface PaiementRepository extends Repository<Paiement>{
        void save(Paiement paiement);
        Paiement findById(int id);
        List<Paiement> findByDette(int detteId);
}
