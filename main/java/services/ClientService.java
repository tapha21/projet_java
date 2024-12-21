package services;

import core.RepoInter.Service;
import entities.Client;
import java.util.List;

public interface ClientService extends Service <Client> {
    void createClient(Client client);
    List<Client> findAll();
    Client search(String tel);
    void update(Client client);  
    List<Client> filterClientsByAccount(List<Client> clients);

}
