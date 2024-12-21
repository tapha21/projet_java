package core.RepoInter;
import java.util.List;

import core.config.Repository;
import entities.Client;
import entities.User;

public interface ClientRepository extends Repository<Client>{
    Client  selectByTel(String tel) ;
    Client  selectBySurname(String name) ;
    List<Client> filterClientsByAccount();
    Client findByUser(User user);
}
