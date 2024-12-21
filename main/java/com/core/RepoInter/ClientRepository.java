package com.core.RepoInter;
import java.util.List;

import com.core.config.Repository;
import com.entities.Client;

public interface ClientRepository extends Repository<Client>{
    Client  selectByTel(String tel) ;
    Client  selectBySurname(String name) ;
    List<Client> filterClientsByAccount();
    
}
