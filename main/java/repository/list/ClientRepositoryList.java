package repository.list;

import core.config.repoImpl.RepositoryImpl;
import core.RepoInter.ClientRepository;
import entities.Client;
import entities.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class ClientRepositoryList extends RepositoryImpl<Client> implements ClientRepository {
    private EntityManager em;

    @Override
    public void update(Client objet) {
        Client clientToUpdate = list.stream()
            .filter(c -> c.getId() == objet.getId())
            .findFirst()
            .orElse(null);
    
        if (clientToUpdate != null) {
            clientToUpdate.setSurname(objet.getSurname());
            clientToUpdate.setTelephone(objet.getTelephone());
            clientToUpdate.setAdresse(objet.getAdresse());
            clientToUpdate.setUser(objet.getUser());
    
            System.out.println("Client mis à jour avec succès !");
        } else {
            System.out.println("Client introuvable.");
        }
    }

    @Override
    public Client selectByTel(String tel) {
        return list.stream()
            .filter(client -> client.getTelephone().compareTo(tel) == 0)
            .findFirst()
            .orElse(null);
    }

    @Override
    public Client selectById(int id) {
        return em.find(Client.class, id);
    }

    @Override
    public Client selectBySurname(String name) {
        return list.stream()
            .filter(client -> client.getSurname().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }

    @Override
    public Client findby(int id) {
        return list.stream()
            .filter(client -> client.getId() == id)
            .findFirst()
            .orElse(null);
    }

    @Override
    public void remove(int id) {
        Client clientToRemove = list.stream()
            .filter(client -> client.getId() == id)
            .findFirst()
            .orElse(null);

        if (clientToRemove != null) {
            list.remove(clientToRemove);
            System.out.println("Client supprimé avec succès !");
        } else {
            System.out.println("Client introuvable.");
        }
    }

    public List<Client> filterClientsByAccount() {
        return list.stream()
            .filter(client -> client.getUser() != null)
            .collect(Collectors.toList());
    }

    @Override
    public Client findByUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUser'");
    }
}
