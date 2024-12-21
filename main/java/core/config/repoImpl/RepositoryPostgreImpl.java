package core.config.repoImpl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import core.Services.YamlService;
import core.Services.impl.YamlServiceImpl;
import core.config.Repository;


public class RepositoryPostgreImpl<T> implements Repository<T> {
   protected Class<T> type;
    protected EntityManager em;
    private EntityManagerFactory emf;
    YamlService yamlService;

    public RepositoryPostgreImpl(Class<T> type) {
        if(em == null){
            yamlService=new YamlServiceImpl();
            Map<String, Object> mapYaml=yamlService.loadyaml();
            String persistance = (String) mapYaml.get("persistance");
            Map<String, Object> persistanceConfig = (Map<String, Object>) mapYaml.get(persistance);
            emf = Persistence.createEntityManagerFactory(persistance, persistanceConfig);
            em=emf.createEntityManager();
        }
        this.type=type;
    }

    @Override
    public void insert(T entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> selectAll() {
        String entityName = type.getSimpleName();
        return this.em.createQuery("SELECT u FROM " + entityName + " u", type).getResultList();
    }

    @Override
    public void update(T objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public T selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }
    @Override
    public T findby(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findby'");
    }

    @Override
    public void remove(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

 

}
