package com.services;

import java.util.List;
import java.util.stream.Collectors;

import com.core.RepoInter.ClientRepository;
// import com.core.inter.ClientRepository;
import com.entities.Client;
// import com.repository.db.clientRepositoryDB;
import com.repository.jpa.ClientJpa;

public class ClientServiceImpl implements ClientService {
    private static int clientIdCounter = 1;
private ClientRepository clientRepository = new ClientJpa();
public ClientServiceImpl() {
    this.clientRepository = new ClientJpa(); // Instance par défaut
}

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    @Override
    public Client search(String tel) {
        return clientRepository.selectByTel(tel);
    }

    @Override
    public void update(Client client) {
       clientRepository.update(client);
    }
    @Override
    public void save(Client objet) {
        objet.setId(clientIdCounter++);
        clientRepository.insert(objet);
    }
    @Override
    public List<Client> find() {
        return clientRepository.selectAll();
    }

    @Override
    public void createClient(Client client) {
        clientRepository.insert(client);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.selectAll();
    }

    public Client findCL(String tel) {
        return clientRepository.selectByTel(tel);
    }

    public void incrementUserIds(List<Client> clients) {
        int idCounter = 1;

        for (Client client : clients) {
            client.setId(idCounter);
            idCounter++;
        }
    }
    @Override
    public List<Client> getAllClients() {
        return clientRepository.selectAll();
    }
    @Override
    public Client getClientBySurname(String surname) {
        return clientRepository.selectBySurname(surname);

    }
}
