package services;

import java.util.List;
import java.util.stream.Collectors;

import core.RepoInter.ClientRepository;
import core.factory.Factory;
// import core.inter.ClientRepository;
import entities.Client;
import entities.User;
// import repository.db.clientRepositoryDB;
import repository.list.ClientRepositoryList;

public class ClientServiceImpl implements ClientService {
    private static int clientIdCounter = 1;
    private ClientRepository clientRepository =Factory.getInstanceClientRepository();

    public ClientServiceImpl(ClientRepository clientRepository2) {
        this.clientRepository = clientRepository2;
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
    public List<Client> filterClientsByAccount(List<Client> clients) {
        return clients.stream()
            .filter(client -> client.getUser() != null)
            .collect(Collectors.toList());
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

    public Client findByUser(User user) {
        return clientRepository.findByUser(user);
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
}
