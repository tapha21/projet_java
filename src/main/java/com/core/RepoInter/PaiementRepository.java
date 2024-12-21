package com.core.RepoInter;

import java.util.List;

import com.core.config.Repository;
import com.entities.Paiement;

public interface PaiementRepository extends Repository<Paiement>{
        void save(Paiement paiement);
        Paiement findById(int id);
        List<Paiement> findByDette(int detteId);
}
