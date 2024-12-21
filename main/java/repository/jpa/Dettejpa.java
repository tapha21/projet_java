package repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import core.RepoInter.DetteRepository;
import core.config.repoImpl.RepositoryJpaImpl;
import entities.Client;
import entities.Dette;

public class Dettejpa extends RepositoryJpaImpl<Dette> implements DetteRepository {
    private EntityManager entityManager;

    public Dettejpa() {
        super(Dette.class);
        this.entityManager = entityManager;
    }

    @Override
    public void save(Dette dette) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(dette);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erreur lors de l'enregistrement de la dette : " + e.getMessage());
        }
    }

    @Override
    public Dette findById(int id) {
        return entityManager.find(Dette.class, id);
    }

    @Override
    public List<Dette> findByClient(Client client) {
        String query = "SELECT d FROM Dette d WHERE d.client = :client";
        return em.createQuery(query, Dette.class)
                            .setParameter("client", client)
                            .getResultList();
    }

    @Override
    public List<Dette> NonSolde(Client client) {
        String query = "SELECT d FROM Dette d WHERE d.client = :client AND d.reglee = false";
        return em.createQuery(query, Dette.class)
                            .setParameter("client", client)
                            .getResultList();
    }

    @Override
    public List<Dette> findByEtat(boolean etat) {
        String query = "SELECT d FROM Dette d WHERE d.reglee = :reglee";
        return em.createQuery(query, Dette.class)
                 .setParameter("reglee", etat)
                 .getResultList();
    }
    
    @Override
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
            System.out.println("Erreur lors de la mise à jour de la dette : " + e.getMessage());
        }
    }

    @Override
    public void remove(int id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Dette dette = findById(id);
            if (dette != null) {
                entityManager.remove(dette);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erreur lors de la suppression de la dette : " + e.getMessage());
        }
    }

    @Override
    public void selectByTel(String tel) {
        String query = "SELECT d FROM Dette d WHERE d.client.tel = :tel";
        List<Dette> dettes = entityManager.createQuery(query, Dette.class)
                                           .setParameter("tel", tel)
                                           .getResultList();
        if (dettes.isEmpty()) {
            System.out.println("Aucune dette trouvée pour ce numéro de téléphone.");
        }
    }
}
