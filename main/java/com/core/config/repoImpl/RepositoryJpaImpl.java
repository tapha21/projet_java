package com.core.config.repoImpl;

import java.util.List;
import java.util.Map;
import com.core.Services.YamlService;
import com.core.Services.impl.YamlServiceImpl;
import com.core.config.Repository;
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
                entityManager.merge(data);
                entityManager.flush();
                entityManager.getTransaction().commit();
                System.err.println("Enregistrement effectué"); 
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                System.err.println("Erreur lors dE L' INSERTION l'entité : " + e.getMessage());
            }  } finally {
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
        em.getTransaction().begin();
        try {
            em.merge(object); 
            em.getTransaction().commit(); 
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
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
