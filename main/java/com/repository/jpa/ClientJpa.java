package com.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.core.RepoInter.ClientRepository;
import com.core.config.repoImpl.RepositoryJpaImpl;
import com.entities.Client;

public class ClientJpa extends RepositoryJpaImpl<Client> implements ClientRepository {
private EntityManager entityManager;

    public ClientJpa() {
        super(Client.class);
        this.entityManager = entityManager;
    }

    @Override
    public Client selectByTel(String tel) {
        String jpql = "SELECT c FROM Client c WHERE c.telephone = :telephone";
        List<Client> clients = em.createQuery(jpql, Client.class)
                                             .setParameter("telephone", tel)
                                             .getResultList();
    
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé pour ce numéro de téléphone.");
            return null; 
        }
    
        return clients.get(0);
    }

    @Override
    public Client selectBySurname(String surname) {
        try {
            TypedQuery<Client> query = this.em.createQuery("SELECT c FROM Client c WHERE c.surname = :surname", Client.class);
            query.setParameter("surname", surname);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Aucun résultat trouvé
        } catch (Exception e) {
            e.printStackTrace(); // Enregistrer l'erreur pour le débogage
            return null; // En cas d'erreur
        }
    }

    @Override
    public List<Client> filterClientsByAccount() {
        throw new UnsupportedOperationException("Unimplemented method 'filterClientsByAccount'");
    }
    public Client getByid(int id) {
        em.getTransaction().begin();
        try {
            Client client = em.find(Client.class, id);
            if (client == null) {
                System.out.println("Client non existant");
                return null;
            }
            return client;
        } finally {
            em.getTransaction().commit();
        }
    }
    

    
}
