package views;

import java.util.List;
import java.util.Scanner;

import core.RepoInter.Service;
import core.RepoInter.View;
import entities.Client;
import entities.User;
import entities.Role;

import enums.Etat;
import services.ClientServiceImpl;
import services.RoleServiceImpl;
import services.UserServiceImpl;

public class ClientVue implements View <Client>  {
    private final Scanner scanner = new Scanner(System.in);
    private final ClientServiceImpl clientServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final RoleServiceImpl roleService;
    public ClientVue(ClientServiceImpl clientServiceImpl ,UserServiceImpl userServiceImpl,RoleServiceImpl roleService) {
        this.clientServiceImpl = clientServiceImpl; 
        this.userServiceImpl = userServiceImpl; 
        this.roleService = roleService; 

    }

    @Override
    public Client saisie() {
        Scanner scanner = new Scanner(System.in);  
        Client client = new Client();
        User user = new User();
        System.out.println("Entrer le surnom");
        client.setSurname(scanner.nextLine());
        System.out.println("Entrer le téléphone");
        client.setTelephone(scanner.nextLine());
        System.out.println("Entrer l'adresse");
        client.setAdresse(scanner.nextLine());
        System.out.println("Associer un utilisateur ? oui/non");
        char res = scanner.next().charAt(0);
        scanner.nextLine(); 
        if (res == 'O' || res == 'o') {
            System.out.println("Entrez le nom: ");
            user.setNom(scanner.nextLine());
            System.out.println("Entrez le prénom: ");
            user.setPrenom(scanner.nextLine());
            System.out.println("Entrez le login: ");
            user.setLogin(scanner.nextLine());
            System.out.println("Entrez le mot de passe: ");
            user.setPassword(scanner.nextLine());
            user.setRole(askRole()); 
            user.setEtat(true);
            client.setUser(user);
            userServiceImpl.save(user);
        }
        clientServiceImpl.save(client);
        System.out.println("Client créé avec succès !");
        return client;
    }

    public Client saisieBoutik() {
        Scanner scanner = new Scanner(System.in);  
        Client client = new Client();
        User user = new User();
        System.out.println("Entrer le surnom");
        client.setSurname(scanner.nextLine());
        System.out.println("Entrer le téléphone");
        client.setTelephone(scanner.nextLine());
        System.out.println("Entrer l'adresse");
        client.setAdresse(scanner.nextLine());
        Role role = new Role();
        role.setNom("boutiquier");
        user.setRole(role);
        client.setUser(user);
        clientServiceImpl.save(client);
        System.out.println("Boutiquier créé avec succès !");
        return client;
    }

    public Role askRole() {
        List<Role> roles = roleService.find();

        if (roles.isEmpty()) {
            System.out.println("Aucun rôle disponible dans la base de données.");
            return null; // Ou gérer autrement
        }
    
        System.out.println("Choisissez un rôle parmi les suivants :");
        for (Role role : roles) {
            System.out.println("- " + role.getNom());
        }
    
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le nom du rôle :");
        String roleInput = scanner.nextLine().trim().toUpperCase();
        // Rechercher le rôle sélectionné
        Role selectedRole = roles.stream()
            .filter(role -> role.getNom().trim().equalsIgnoreCase(roleInput))
            .findFirst()
            .orElse(null);
    
        if (selectedRole == null) {
            System.out.println("Rôle invalide. Réessayez.");
            return askRole(); 
        }
    
        return selectedRole;
    }

    public void affiche() {
        List<Client> clientList = clientServiceImpl.find();
        for (Client client : clientList) {
            System.out.println(String.format("ID:%-5s Surname:%-10s Téléphone:%-10s",
            client.getId(),
            client.getSurname().trim(),
            client.getTelephone().trim()));
        }
    }

    public void recherche() {
        System.out.println("Entrer le téléphone");
        String tel = scanner.nextLine();
        Client client = clientServiceImpl.search(tel);
        if (client == null) {
            System.out.println("Aucun client trouvé.");
        } else {
            System.out.println("Client trouvé: " + client);
        }
    }

    @Override
    public void afficher(List<Client> clients) {
        for (Client client : clients) {
            System.out.println(String.format("ID:%-5s Surname:%-10s Téléphone:%-10s",
                client.getId(),
                client.getSurname().trim(),
                client.getTelephone().trim()));
    }
        }
    

    public void pasdeCompte() {
        List<Client> clientList = clientServiceImpl.filterClientsByAccount(clientServiceImpl.find());
        if (clientList.isEmpty()) {
            System.out.println("Aucun client trouvé.");
        } else {
            clientList.forEach(System.out::println);
        }
    }
}
