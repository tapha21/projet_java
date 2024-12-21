package com.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.core.RepoInter.PaiementRepository;
import com.core.config.repoImpl.RepositoryJpaImpl;
import com.entities.Client;
import com.entities.Dette;
import com.entities.Paiement;


public class Paiementjpa extends RepositoryJpaImpl<Paiement> implements PaiementRepository {
    private EntityManager entityManager;

    public Paiementjpa() {
        super(Paiement.class);
        this.em = em;
    }

    @Override
    public void save(Paiement paiement) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(paiement);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erreur lors de l'enregistrement du paiement : " + e.getMessage());
        }
    }

    @Override
    public Paiement findById(int id) {
        return entityManager.find(Paiement.class, id);
    }

    @Override
    public List<Paiement> findByDette(int detteId) {
        String query = "SELECT p FROM Paiement p WHERE p.dette.id = :detteId";
        return entityManager.createQuery(query, Paiement.class)
                            .setParameter("detteId", detteId)
                            .getResultList();
    }

    // Méthode pour trouver les dettes d'un client
    public List<Dette> findByClient(Client client) {
        String query = "SELECT d FROM Dette d WHERE d.client = :client";
        return entityManager.createQuery(query, Dette.class)
                            .setParameter("client", client)
                            .getResultList();
    }

    // Méthode pour trouver les dettes non soldées d'un client
    public List<Dette> findNonSolde(Client client) {
        String query = "SELECT d FROM Dette d WHERE d.client = :client AND d.solde > 0";
        return entityManager.createQuery(query, Dette.class)
                            .setParameter("client", client)
                            .getResultList();
    }

    // Méthode pour trouver les dettes selon leur état (solde ou non)
    public List<Dette> findByEtat(boolean etat) {
        String query = "SELECT d FROM Dette d WHERE d.etat = :etat";
        return entityManager.createQuery(query, Dette.class)
                            .setParameter("etat", etat)
                            .getResultList();
    }

    // Méthode pour mettre à jour une dette
    public void update(Dette dette) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(dette);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erreur lors de la mise à jour de la dette : " + e.getMessage());
        }
    }

    // Méthode pour supprimer une dette
    public void remove(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Dette dette = entityManager.find(Dette.class, id);
            if (dette != null) {
                entityManager.remove(dette);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erreur lors de la suppression de la dette : " + e.getMessage());
        }
    }

    // Méthode pour trouver les dettes d'un client via son numéro de téléphone
    public void selectByTel(String tel) {
        String query = "SELECT d FROM Dette d WHERE d.client.tel = :tel";
        List<Dette> dettes = entityManager.createQuery(query, Dette.class)
                                           .setParameter("tel", tel)
                                           .getResultList();
        if (dettes.isEmpty()) {
            System.out.println("Aucune dette trouvée pour le numéro de téléphone : " + tel);
        } else {
            for (Dette d : dettes) {
                System.out.println(d);
            }
        }
    }
}
