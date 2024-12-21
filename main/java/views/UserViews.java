package views;

import java.util.List;
import java.util.Scanner;

import core.RepoInter.View;
import entities.Client;
import entities.User;
import entities.Role;

import services.ClientServiceImpl;
import services.UserServiceImpl;

public class UserViews implements View <User> {
    private final Scanner scanner = new Scanner(System.in);
    
    // Dépendances des services
    private final ClientServiceImpl clientServiceImpl;
    private final UserServiceImpl userServiceImpl;
    
    // Constructeur pour injecter les services
    public UserViews(ClientServiceImpl clientServiceImpl, UserServiceImpl userServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    // Méthode de saisie d'un nouvel utilisateur
    public User saisie() {
        Scanner scanner = new Scanner(System.in);  

        User user = new User();
        
        System.out.println("Entrez le nom: ");
        user.setNom(scanner.nextLine());
        
        System.out.println("Entrez le prénom: ");
        user.setPrenom(scanner.nextLine());
        
        System.out.println("Entrez le login: ");
        user.setLogin(scanner.nextLine());
        
        System.out.println("Entrez le mot de passe: ");
        user.setPassword(scanner.nextLine());
        
        user.setEtat(true);  // L'état par défaut est 'true' pour 'activer'
        
        user.setRole(askRole());  
        
        return user;
    }

    // Méthode pour demander le rôle de l'utilisateur
    public Role askRole() {
        Scanner scanner = new Scanner(System.in);
        String roleInput;
        System.out.println("Choisir un rôle: ADMIN, USER, CLIENT");
        roleInput = scanner.nextLine().toUpperCase();
        Role role = new Role();
        switch (roleInput) {
            case "ADMIN":
                role.setNom("ADMIN");
                break;
            case "USER":
                role.setNom("USER");
                break;
            case "CLIENT":
                role.setNom("CLIENT");
                break;
            default:
                role.setNom("CLIENT");
                break;
        }
        return role;
    }

    public void creerUtilisateurAvecClient() {
        System.out.println("Entrez le numéro de téléphone du client : ");
        String telS = scanner.nextLine();
        Client client = clientServiceImpl.search(telS);
        
        if (client == null) {
            System.out.println("Aucun client trouvé avec ce numéro.");
            return;
        }
        
        if (client.getUser() != null) {
            System.out.println("Ce client a déjà un compte utilisateur.");
            return;
        }
        
        User user = saisie();
        if (user == null) {
            System.out.println("Échec de la création de l'utilisateur.");
            return;
        }
        List<User> users = userServiceImpl.findAll(); 
        userServiceImpl.incrementUserIds(users); 
        userServiceImpl.create(user);
        client.setUser(user);
        clientServiceImpl.update(client);   
        System.out.println("Utilisateur créé avec succès !");
    }
    

    public void recherchebytel(){
        System.out.println("Entrez le numéro de téléphone : ");
        String tel = scanner.nextLine();
        Client client = clientServiceImpl.search(tel);
        if (client == null) {
            System.out.println("Aucun client trouvé avec ce numéro.");
        } else {
            System.out.println("Client trouvé : " + client);
        }
    }

     public void afficherClientsAvecCompte() {
        List<Client> clients = clientServiceImpl.findAll();
        List<Client> clientsAvecCompte = clientServiceImpl.filterClientsByAccount(clients); 
        if (clientsAvecCompte.isEmpty()) {
            System.out.println("Aucun client n'a de compte utilisateur.");
        } else {
            System.out.println("Clients ayant un compte utilisateur :");
            for (Client client : clientsAvecCompte) {
                afficherClient(client);
            }
        }
    
    }
    
    public void affiche() {
        List<User> userList = userServiceImpl.findAll();
        afficher(userList);
}
        
    @Override
    public void afficher(List<User> users) {
        for (User user : users) {
            System.out.println(String.format("ID:%-5d Nom:%-10s Prénom:%-10s Rôle:%-10s",
                    user.getId(),                       
                    user.getNom() != null ? user.getNom().trim() : " ",
                    user.getPrenom() != null ? user.getPrenom().trim() : " ",
                    user.getRole() != null ? user.getRole().toString().trim() : " "));
        }
    }

    
    private void afficherClient(Client client) {
        System.out.println(String.format("Nom: %-10s, Téléphone: %-10s, Adresse: %-10s, Utilisateur: %s",
            client.getSurname() != null ? client.getSurname().trim() : "",
            client.getTelephone() != null ? client.getTelephone() : "",
            client.getAdresse() != null ? client.getAdresse().trim() : "",
            client.getUser() != null 
                ? String.format("Login: %s, Prénom: %s, Nom: %s", 
                                client.getUser().getLogin() != null ? client.getUser().getLogin() : "",
                                client.getUser().getPrenom() != null ? client.getUser().getPrenom() : "",
                                client.getUser().getNom() != null ? client.getUser().getNom() : "")
                : "Aucun utilisateur associé"));
    }
  
   public User connexion() {
            System.out.println("Entrez le login : ");
            String login = scanner.nextLine();
            System.out.println("Entrez le mot de passe : ");
            String password = scanner.nextLine();
            User user = userServiceImpl.Connection(login, password);
    
            if (user != null) {
                System.out.println("Utilisateur trouvé : " + user.getPrenom());
                return user;
            } else {
                System.out.println("Utilisateur non trouvé !");
                return null;
            }
    }
// }


public void afficherMenu(String roleName) {
    System.out.println("Rôle avant comparaison : " + roleName);
    if (roleName != null) {
        switch (roleName.trim().toLowerCase()) {
            case "admin":
                break;
            case "client":
                break;
            case "boutiquier":
                break;
            default:
                System.out.println("Rôle inconnu, aucun menu disponible.");
        }
    } else {
        System.out.println("Erreur : Le rôle est nul. Veuillez vérifier la connexion.");
    }
    System.out.println("\nVeuillez choisir une option : ");
}


    
}
