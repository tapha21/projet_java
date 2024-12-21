package com.core.RepoInter;

import java.util.List;

import com.core.config.Repository;
import com.entities.Article;
import com.entities.Client;
import com.entities.Dette;

public interface DetteRepository extends Repository<Dette> {
     void selectByTel(String tel) ;
     void save(Dette dette);
     Dette findById(int id);
     List<Dette>findByClient(Client client);
     void update(Dette dette);
     List<Dette> NonSolde(Client client);
     List<Dette> findByEtat(boolean etat);
}
