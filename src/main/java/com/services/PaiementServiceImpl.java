package com.services;

import java.util.List;

import com.core.RepoInter.DetailsRepository;
import com.core.RepoInter.PaiementRepository;
import com.core.RepoInter.Service;
import com.core.factory.Factory;
import com.entities.Article;
import com.entities.Dette;
import com.entities.Paiement;

public class PaiementServiceImpl implements Service <Paiement>{
     Paiement paiement;
     private static int clientIdCounter = 1;

     PaiementRepository paiementRepository =Factory.getInstancePaiment();
        private DetailsRepository detailsRepository = Factory.getInstanceDetails();

      public PaiementServiceImpl(PaiementRepository paiementRepositori,DetailsRepository detailsRepository){
        this.paiementRepository=paiementRepositori;
        this.detailsRepository = detailsRepository;

    }

    @Override
    public void save(Paiement objet) {
        objet.setId(clientIdCounter++);
        paiementRepository.save(objet);
        System.out.println("Paiement sauvegardé avec succès !");
    }



    @Override
    public List<Paiement> find() {
        List<Paiement> paiements = paiementRepository.selectAll();
        if (paiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé.");
        }
        return paiements;
    }



    @Override
    public void update(Paiement objet) {
        Paiement paiementExistant = paiementRepository.findById(paiement.getId());
        if (paiementExistant != null) {
            paiementRepository.update(paiement);
            System.out.println("Paiement mis à jour avec succès !");
        } else {
            System.out.println("Paiement non trouvé !");
        }
    }

     public void incrementIds(List<Paiement> paiements) {
        int idCounter = 1; 

        for (Paiement Paiement : paiements) {
            Paiement.setId(idCounter); 
            idCounter++; 
        }
    }
   
   public List<Paiement> findByDette(Dette dette) {
        return detailsRepository.findDette(dette);
    }
}
