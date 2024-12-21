package repository.jpa;

import core.config.repoImpl.RepositoryJpaImpl;
import java.util.List;
import core.RepoInter.ClientRepository;
import entities.Client;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.NoResultException;

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
    public Client getClientByUser(User user) {
        em.getTransaction().begin();
        try {
            Client client = em.createQuery("SELECT c FROM Client c WHERE c.user = :user", Client.class)
                    .setParameter("user", user)
                    .getSingleResult();
            if (client == null) {
                System.out.println("Client non existant");
                return null;
            }
            return client;
        } finally {
            em.getTransaction().commit();
        }
    }

    @Override
    public Client findByUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUser'");
    }

}
