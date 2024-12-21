import java.util.Scanner;

import core.RepoInter.ClientRepository;
import core.RepoInter.DetailsRepository;
import core.RepoInter.DetteRepository;
import core.RepoInter.PaiementRepository;
import core.RepoInter.UserRepository;
import core.factory.Factory;
import core.factory.facto.RepositoryFactory;
import entities.User;
import services.ArticleServiceImpl;
import services.ClientServiceImpl;
import services.DetailsServiceImpl;
import services.UserServiceImpl;
import services.DetteServiceImpl;
import services.PaiementServiceImpl;
import services.RoleServiceImpl;
import views.ArticleVue;
import views.ClientVue;
import views.UserViews;
import views.DetteVue;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Services
        ClientRepository clientRepository = (ClientRepository) RepositoryFactory.getRepository(ClientRepository.class);
        UserRepository userRepository = (UserRepository) RepositoryFactory.getRepository(UserRepository.class);
        DetteRepository detteRepository = (DetteRepository) RepositoryFactory.getRepository(DetteRepository.class);
        DetailsRepository detailsRepository = (DetailsRepository) RepositoryFactory.getRepository(DetailsRepository.class);
        PaiementRepository paiementRepository = (PaiementRepository) RepositoryFactory.getRepository(PaiementRepository.class);
        ClientServiceImpl clientServiceImpl = new ClientServiceImpl(clientRepository);
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository);
        DetteServiceImpl detteServiceImpl = new DetteServiceImpl(detteRepository);
        DetailsServiceImpl detailsServiceImpl = new DetailsServiceImpl(detailsRepository);
        PaiementServiceImpl paiementServiceImpl = new PaiementServiceImpl(paiementRepository, detailsRepository);
        ArticleServiceImpl articleServiceImpl = new ArticleServiceImpl(Factory.getInstanceArticleRepository(),Factory.getInstanceDetails());
        RoleServiceImpl roleService = new RoleServiceImpl(Factory.getInstanceRoleRepository());
        ClientVue clientVue = new ClientVue(clientServiceImpl,userServiceImpl,roleService);
        UserViews userVue = new UserViews(clientServiceImpl, userServiceImpl);
        DetteVue detteVue = new DetteVue(clientServiceImpl, detteServiceImpl,articleServiceImpl,paiementServiceImpl);
        ArticleVue articleVue = new ArticleVue(articleServiceImpl,detteServiceImpl);


        

            User user = userVue.connexion();
            if (user != null) {
                System.out.println("Connexion réussie !");
                String roleName = userServiceImpl.findrole(user.getLogin());
                System.out.println("Rôle récupéré : " + roleName);
             afficherMenu(scanner, roleName, clientVue, userVue, detteVue, articleVue);   
         } else {
                System.out.println("Échec de la connexion. Vérifiez vos identifiants.");
            }
        }
        
        public static void afficherMenu(Scanner scanner, String roleName, ClientVue clientVue, UserViews userVue, DetteVue detteVue, ArticleVue articleVue) {
            int choix;
            
            do {
                if (roleName != null) {
                    switch (roleName.trim().toLowerCase()) {
                        case "admin":
                            System.out.println("Menu Admin");
                            System.out.println("1- Créer un utilisateur avec un role client"); 
                            System.out.println("2- Créer un utilisateur avec un role boutiquier"); 
                            System.out.println("3- Activer ou desactiver un compte"); 
                            System.out.println("4- Enregistrer un Article");
                            System.out.println("5- Lister les Articles");
                            System.out.println("6- Mettre à jour le stock d'un article");
                            System.out.println("7- Archiver dette solde");
                            System.out.println("8- Déconnexion");
                            System.out.print("Votre choix : ");
                            choix = scanner.nextInt();
                            scanner.nextLine();
                            switch (choix) {
                                case 1:
                                    clientVue.saisie();
                                    break;
                                case 2:
                                    clientVue.saisieBoutik();
                                    break;
                                case 3:
                                    // userVue.activerDesactiverCompte();
                                    break;
                                case 4:
                                    articleVue.saisie();
                                    break;
                                case 5:
                                    articleVue.affiche();
                                    break;
                                case 6:
                                    articleVue.mettreAJourQuantiteEnStock();
                                    break;
                                case 7:
                                    // detteVue.archiverDetteSolde();
                                    break;
                                case 8:
                                    System.out.println("Déconnexion réussie !");
                                    return; 
                                default:
                                    System.out.println("Choix non valide !");  
                            }
                            break;
        
                        case "client":
                            System.out.println("Menu Client");
                            System.out.println("1- Lister les dettes non solde");
                            System.out.println("2- Faire une demande de dettes ");
                            System.out.println("3- Lister ses demandes de dette ");
                            System.out.println("4- Envoyer une relance");
                            System.out.println("5. Déconnexion");
                            choix = scanner.nextInt();
                            scanner.nextLine();
                            switch (choix) {
                                case 1:
                                    detteVue.afficherDettesNonSolde();
                                    break;
                                case 2:
                                    detteVue.saisieDemandeDette();
                                    break;
                                case 3:
                                    detteVue.listerDemandesDeDetteClient();
                                    break;
                                case 4:
                                    // detteVue.envoyerRelance();
                                    break;
                                case 5:
                                    System.out.println("Déconnexion réussie !");
                                    return; // Quitter la méthode et donc revenir au programme principal
                                default:
                                    System.out.println("Choix non valide !");                                  
                            };
                            break;
        
                        case "boutiquier":
                            System.out.println("Menu Boutiquier");
                            System.out.println("1- Créer un client");
                            System.out.println("2- Lister les clients");
                            System.out.println("3- Rechercher les clients qui ont un compte");
                            System.out.println("4- Rechercher un client par téléphone");
                            System.out.println("5- Enregistrer une dette");
                            System.out.println("6- Payer une dette");
                            System.out.println("7- Lister les dettes non solde");
                            System.out.println("8- Lister les dettes d'un client");
                            System.out.println("9- Voir les demandes de dettes en cours");
                            System.out.println("10- Déconnexion");
                            choix = scanner.nextInt();
                            scanner.nextLine();
                            switch (choix) {
                                case 1:
                                    clientVue.saisie();
                                    break;
                                case 2:
                                    clientVue.affiche();
                                    break;
                                case 3:
                                    clientVue.pasdeCompte();
                                    break;
                                case 4:
                                    clientVue.recherche();
                                    break;
                                case 5:
                                    detteVue.saisie();
                                    break;
                                case 6:
                                    detteVue.payerDette();
                                    break;
                                case 7:
                                    detteVue.afficherDettesNonSolde();
                                    break;
                                case 8:
                                    detteVue.listerDettesClient();
                                    break;
                                case 9:
                                    detteVue.listerDemandesDeDetteEnCours();
                                    break;
                                case 10:
                                    System.out.println("Déconnexion réussie !");
                                    return; // Quitter la méthode et donc revenir au programme principal
                                default:
                                    System.out.println("Choix non valide, veuillez réessayer");
                            };
                            break;
        
                        default:
                            System.out.println("Rôle inconnu, aucun menu disponible.");
                    }
                } else {
                    System.out.println("Erreur : Le rôle est nul. Veuillez vérifier la connexion.");
                }
            } while (true);  
        }
        
        }
        
    
        
 