package core.RepoInter;

import java.util.List;

public interface Service <T>{
        void save(T objet);
        List<T> find();
        void update(T objet);
        
}
