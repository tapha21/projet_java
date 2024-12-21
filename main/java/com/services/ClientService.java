package com.services;

import com.core.RepoInter.Service;
import com.entities.Client;
import java.util.List;

public interface ClientService extends Service <Client> {
    void createClient(Client client);
    List<Client> findAll();
    Client search(String tel);
    void update(Client client);  
    List<Client> getAllClients();
    Client getClientBySurname(String surname);

}
