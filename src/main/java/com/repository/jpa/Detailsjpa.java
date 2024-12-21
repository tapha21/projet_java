package com.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.core.RepoInter.DetailsRepository;
import com.core.config.repoImpl.RepositoryJpaImpl;
import com.entities.Article;
import com.entities.Detail;
import com.entities.Dette;
import com.entities.Paiement;

public class Detailsjpa extends RepositoryJpaImpl<Detail> implements DetailsRepository {
    private EntityManager entityManager;

    public Detailsjpa() {
        super(Detail.class);
        this.entityManager = entityManager;
    }

    @Override
    public List<Article> findByDette(Dette dette) {
        String query = "SELECT d.article FROM Detail d WHERE d.dette = :dette";
        return entityManager.createQuery(query, Article.class)
                            .setParameter("dette", dette)
                            .getResultList();
    }

    @Override
    public List<Paiement> findDette(Dette dette) {
        String query = "SELECT p FROM Paiement p WHERE p.dette = :dette";
        return entityManager.createQuery(query, Paiement.class)
                            .setParameter("dette", dette)
                            .getResultList();
    }

    @Override
    public void insert(Detail detail) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(detail);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erreur lors de l'insertion du détail : " + e.getMessage());
        }
    }

    @Override
    public void update(Detail detail) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(detail);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erreur lors de la mise à jour du détail : " + e.getMessage());
        }
    }

    @Override
    public List<Detail> selectAll() {
        String query = "SELECT d FROM Detail d";
        return entityManager.createQuery(query, Detail.class).getResultList();
    }

    @Override
    public Detail selectById(int id) {
        return entityManager.find(Detail.class, id);
    }

    @Override
    public void remove(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Detail detail = selectById(id);
            if (detail != null) {
                entityManager.remove(detail);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erreur lors de la suppression du détail : " + e.getMessage());
        }
    }

    @Override
    public Detail findby(int id) {
        return selectById(id);
    }
}
