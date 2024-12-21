package core.config.repoImpl;

import java.util.List;
import java.util.Map;
import core.Services.YamlService;
import core.Services.impl.YamlServiceImpl;
import core.config.Repository;
import entities.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class RepositoryJpaImpl<T> implements Repository<T> {
    protected Class<T> entityClass;
    protected EntityManager em;
    private EntityManagerFactory emf;
    YamlService yamlService;

    public RepositoryJpaImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
        yamlService = new YamlServiceImpl();
        Map<String, Object> mapYaml = yamlService.loadyaml();
        emf = Persistence.createEntityManagerFactory(mapYaml.get("persistance").toString());
        em = emf.createEntityManager();
    }

    @Override
    public void insert(T data) {
        EntityManager entityManager = emf.createEntityManager();
    try {
        entityManager.getTransaction().begin();
        try {
            if (((AbstractEntity) data).getId() == 0) {
                entityManager.persist(data);
            } else {
                entityManager.merge(data);
            }
            entityManager.flush();
            entityManager.getTransaction().commit();
            System.out.println("Enregistrement effectué");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erreur lors de l'insertion : " + e.getMessage());
        }
    } finally {
        entityManager.close();
    }
    }
        
    @Override
    public T selectById(int id) {
        return em.find(entityClass, id);
    }

    @Override
    public List<T> selectAll() {
        String sql = String.format("SELECT e FROM %s e", entityClass.getSimpleName());
        return this.em.createQuery(sql, entityClass).getResultList();
    }

    @Override
    public void update(T object) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(object);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erreur lors de la mise à jour : " + e.getMessage());
        } finally {
            entityManager.close();
        }           
    }

    @Override
    public void remove(int id) {
        em.getTransaction().begin();
        try {
            T entity = selectById(id);
            if (entity != null) {
                em.remove(entity);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erreur lors de la suppression de l'entité : " + e.getMessage());
        }
    }

    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Override
    public T findby(int id) {
        return selectById(id);
    }

 
}
