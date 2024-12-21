package com.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import com.core.config.repoImpl.RepositoryJpaImpl;
import com.core.RepoInter.UserRepository;
import com.entities.User;

public class UserJpa extends RepositoryJpaImpl<User> implements UserRepository {
    private EntityManager em;

    public UserJpa() {
        super(User.class);
        // Initialize EntityManager here
        this.em = Persistence.createEntityManagerFactory("Sama").createEntityManager();
    }

    @Override
    public List<User> selectByRole(String role) {
        return this.em.createNamedQuery("selectByRole", User.class)
                     .setParameter("role", role)
                     .getResultList();
    }

    @Override
    public List<User> selectActive(boolean active) {
        throw new UnsupportedOperationException("Unimplemented method 'selectActive'");
    }

    @Override
    public User selectByID(int id) {
        try {
            return this.em.createNamedQuery("selectByID", User.class)
                          .setParameter("id", id)
                          .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User selectByLogin(String login) {
        return this.em.createNamedQuery("selectByLogin", User.class)
                      .setParameter("login", login)
                      .getSingleResult();
    }

    @Override
    public List<User> search(String tel) {
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        try {
            return this.em.createQuery(
                    "SELECT u FROM User u WHERE u.login = :login AND u.password = :password", User.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Aucun utilisateur trouvé pour ces identifiants");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
