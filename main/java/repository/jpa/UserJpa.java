package repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import core.config.repoImpl.RepositoryJpaImpl;
import core.RepoInter.UserRepository;
import entities.User;

public class UserJpa extends RepositoryJpaImpl<User> implements UserRepository {
    protected EntityManager em;

    public UserJpa() {
        super(User.class);
        this.em = em;

    }

    @Override
    public List<User> selectByRole(String role) {
        return this.em.createNamedQuery("selectByRole", User.class)
                     .setParameter("role", role)
                     .getResultList();
    }

    @Override
    public List<User> selectActive(boolean active) {
        // TODO: Implémenter cette méthode
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
    public User selectByLoginAndPassword(String Login, String password) {
        return this.em.createNamedQuery("selectByLogin", User.class)
                      .setParameter("login", Login)
                      .setParameter("password", password)
                      .getSingleResult();
    }

    @Override
    public List<User> search(String tel) {
        // TODO: Implémenter cette méthode
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    @Override
    public String getRoleByLogin(String login) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRoleByLogin'");
    }
}
