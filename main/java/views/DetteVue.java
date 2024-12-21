package views;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import core.RepoInter.View;
import entities.Article;
import entities.Client;
import entities.Dette;
import entities.Paiement;
import entities.User;
import services.ArticleServiceImpl;
import services.ClientServiceImpl;
import services.DetteServiceImpl;
import services.PaiementServiceImpl;

public class DetteVue implements View<Dette> {
    private ClientServiceImpl clientServiceImpl;
    private DetteServiceImpl detteServiceImpl;
    private ArticleServiceImpl articleServiceImpl;
    private PaiementServiceImpl paiementServiceImpl;
    private Scanner scanner;

    public DetteVue(ClientServiceImpl clientServiceImpl, DetteServiceImpl detteServiceImpl,
                    ArticleServiceImpl articleServiceImpl, PaiementServiceImpl paiementServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
        this.detteServiceImpl = detteServiceImpl;
        this.articleServiceImpl = articleServiceImpl;
        this.paiementServiceImpl = paiementServiceImpl;
        this.scanner = new Scanner(System.in);
    }

    public Dette saisie() {
        Client client = findClient();
        if (client == null) return null;
        List<Article> selectedArticles = selectArticles();
        if (selectedArticles.isEmpty()) return null;
        double montantVerser = askMontantVerser();
        Dette dette = createDette(client, selectedArticles, montantVerser);
        detteServiceImpl.enregistrerDette(dette);
        System.out.println("Dette enregistrée avec succès pour le client " + client.getSurname());

        return dette;
    }

    private Client findClient() {
        System.out.println("Entrez le numéro du client  : ");
        String clientId = scanner.nextLine();
        Client client = clientServiceImpl.findCL(clientId);
        if (client == null) {
            System.out.println("Client non trouvé !");
        }
        return client;
    }

    private List<Article> selectArticles() {
        List<Article> articles = articleServiceImpl.find();
        if (articles.isEmpty()) {
            System.out.println("Aucun article disponible.");
            return new ArrayList<>();
        }
        List<Article> selectedArticles = new ArrayList<>();
        boolean continuerSelection = true;
        do {
        System.out.println("Liste des articles :");
        for (int i = 0; i < articles.size(); i++) {
            System.out.println((i + 1) + ". " + articles.get(i).getLibelle() + " - Prix: " + articles.get(i).getPrix() + " - Quantité: " + articles.get(i).getQuantityInStock());
        }
    
        System.out.println("Entrez les numéros des articles que vous souhaitez acheter (séparés par des virgules) : ");
        String[] articleIds = scanner.nextLine().split(",");
    
       
        for (String articleId : articleIds) {
            try {
                int id = Integer.parseInt(articleId.trim()) - 1;
                if (id >= 0 && id < articles.size()) {
                    Article article = articles.get(id);
                    System.out.println("Entrez la quantité que vous souhaitez acheter pour l'article " + article.getLibelle() + " : ");
                    int quantite = Integer.parseInt(scanner.nextLine());
                    if (quantite > article.getQuantityInStock()) {
                        System.out.println("La quantité demandée est supérieure à la quantité disponible.");
                    } else {
                        article.setQuantiteSelectionnee(quantite);
                        article.setQuantityInStock(article.getQuantityInStock() - quantite);
                        if (article.getQuantityInStock() <= 0) {
                            article.setEtat(false);
                            System.out.println("L'article " + article.getLibelle() + " a été désactivé car la quantité est inférieure ou égale à 0.");
                        }
                        articleServiceImpl.update(article);
                        selectedArticles.add(article);
                    }
                } else {
                    System.out.println("Numéro d'article invalide : " + (id + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide : " + articleId);
            }
        }  System.out.println("Voulez-vous sélectionner d'autres articles ? (oui/non) : ");
        String reponse = scanner.nextLine().trim().toLowerCase();
        continuerSelection = reponse.equals("oui");

    } while (continuerSelection);
        return selectedArticles;
    }
    
    private double askMontantVerser() {
        double montantVerser = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("Entrez le montant versé par le client : ");
                montantVerser = Double.parseDouble(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un montant valide.");
            }
        }
        return montantVerser;
    }

    private Dette createDette(Client client, List<Article> selectedArticles, double montantVerser) {
        double montant = 0;
        for (int i = 0; i < selectedArticles.size(); i++) {
            Article article = selectedArticles.get(i);
            montant += article.getPrix()*article.getQuantiteSelectionnee(); 
        }        
        Dette dette = new Dette();
        dette.setClient(client);
        dette.setMontant(montant);
        dette.setMontantVerser(montantVerser);
        dette.setReglee(false);
        dette.setDate(LocalDateTime.now());
        dette.setArticles(selectedArticles);
        double montantRestant = montant - montantVerser;
        if (montantRestant <= 0) {
            dette.setReglee(true);
            System.out.println("La dette a été entièrement payée.");
        } else {
            System.out.println("Il reste " + montantRestant + " à payer.");
        }
        return dette;
    }

    @Override
    public void afficher(List<Dette> dettes) {
        for (Dette dette : dettes) {
            System.out.println("ID: " + dette.getId() + ", Client: " + dette.getClient().getSurname() + ", Montant: " + dette.getMontant() + ", Réglée: " + dette.isReglee());
        }
    }

    public void payerDette() {
        int detteId = askDetteId();
        Dette dette = detteServiceImpl.findById(detteId);
        if (dette == null) {
            System.out.println("Dette non trouvée !");
            return;
        }
        if (dette.estReglee()) {
            System.out.println("Cette dette est déjà payée.");
        } else {
            double montantPaye = askMontantPaye();
            double montantTotalVerse = dette.getMontantVerser() + montantPaye;
            double montantRestant = dette.getMontant() - montantTotalVerse;
            dette.setMontantVerser(montantTotalVerse);
            if (montantRestant <= 0) {
                dette.setReglee(true);
                System.out.println("La dette a été entièrement payée.");
            } else {
                System.out.println("Il reste " + montantRestant + " à payer.");
            }
            Paiement paiement = new Paiement();
            paiement.setMontant(montantPaye);
            dette.setDate(LocalDateTime.now());
            paiement.setDette(dette);
            paiementServiceImpl.save(paiement); 
            System.out.println("Paiement enregistré avec succès.");
            detteServiceImpl.mettreAJour(dette);
            System.out.println("Mise à jour de la dette réussie.");
        }
    }

    private int askDetteId() {
        int detteId = -1;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("Entrez l'ID de la dette à payer : ");
                detteId = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un ID valide.");
            }
        }
        return detteId;
    }

    private double askMontantPaye() {
        double montantPaye = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("Entrez le montant à payer : ");
                montantPaye = Double.parseDouble(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un montant valide.");
            }
        }
        return montantPaye;
    }

    public void listerDettesClient() {
        Client client = findClient();
        if (client == null) return;
        List<Dette> dettes = detteServiceImpl.findByClient(client);
        if (dettes.isEmpty()) {
            System.out.println("Ce client n'a aucune dette.");
        } else {
            afficher(dettes);
        }
    }

    public void afficherDettesNonSolde() {
        Client client = findClient();
        if (client == null) return;
        List<Dette> dettesNonSolde = detteServiceImpl.solde(client);
        if (dettesNonSolde.isEmpty()) {
            System.out.println("Ce client n'a aucune dette non soldée.");
        } else {
            afficherNonSolde(dettesNonSolde);
        }
    }

    public void afficherMesDettesNonSolde(User userConnecte) {
    if (userConnecte == null) {
        System.out.println("Utilisateur non connecté !");
        return;
    }

    // Récupérer le client à partir de l'utilisateur connecté
    Client client = findClientByUser(userConnecte);
    if (client == null) {
        System.out.println("Aucun client associé à cet utilisateur.");
        return;
    }
    

    // Récupérer les dettes non soldées du client
    List<Dette> dettesNonSolde = detteServiceImpl.solde(client);
    if (dettesNonSolde.isEmpty()) {
        System.out.println("Ce client n'a aucune dette non soldée.");
    } else {
        afficherNonSoldes(dettesNonSolde);
    }
}

private Client findClientByUser(User user) {
    return clientServiceImpl.findByUser(user);
}

private void afficherNonSoldes(List<Dette> dettesNonSolde) {
    for (Dette dette : dettesNonSolde) {
        double montantRestant = dette.getMontant() - dette.getMontantVerser();
        System.out.println("Dette ID: " + dette.getId() + ", Montant: " + dette.getMontant() + ", Montant restant: " + montantRestant);
    }
}

    private void afficherNonSolde(List<Dette> dettesNonSolde) {
        System.out.println("Liste des dettes non soldées du client :");
        for (Dette dette : dettesNonSolde) {
            System.out.println(dette);
        }

        System.out.println("Voulez-vous afficher les articles ou les paiements ?");
        System.out.println("1. Articles");
        System.out.println("2. Paiements");
        int choix = askChoix();

        switch (choix) {
            case 1:
                afficherArticles(dettesNonSolde);
                break;
            case 2:
                afficherPaiements(dettesNonSolde);
                break;
            default:
                System.out.println("Choix non valide.");
                break;
        }
    }

    private int askChoix() {
        int choix = -1;
        boolean validInput = false;
        while (!validInput) {
            try {
                choix = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un choix valide.");
            }
        }
        return choix;
    }

    private void afficherArticles(List<Dette> dettesNonSolde) {
        for (Dette dette : dettesNonSolde) {
            System.out.println("Articles de la dette ID " + dette.getId() + ":");
            for (Article article : dette.getArticles()) {
                System.out.println(article.getLibelle() + " - Prix: " + article.getPrix());
            }
        }
    }   

    private void afficherPaiements(List<Dette> dettesNonSolde) {
        for (Dette dette : dettesNonSolde) {
            System.out.println("Paiements de la dette ID " + dette.getId() + ":");
            List<Paiement> paiements = paiementServiceImpl.findByDette(dette);
            for (Paiement paiement : paiements) {
                System.out.println("Montant: " + paiement.getMontant() + ", Date: " + paiement.getDate());
            }
        }
    }
  
    public void listerDemandesDeDetteEnCours() {
        System.out.println("Entrez l'état de la demande de dette que vous souhaitez afficher (1 pour En Cours, 2 pour Annuler) : ");
        int etat = scanner.nextInt();
        scanner.nextLine();
        boolean etatBoolean = (etat == 1);
        List<Dette> demandesDeDetteEnCours = detteServiceImpl.findByEtat(etatBoolean);
        for (Dette demandeDeDette : demandesDeDetteEnCours) {
            System.out.println("ID : " + demandeDeDette.getId());
            System.out.println("Client : " + (demandeDeDette.getClient() != null ? demandeDeDette.getClient().getSurname() : "N/A"));
            System.out.println("Montant : " + demandeDeDette.getMontant());
            System.out.println("Etat : " + (demandeDeDette.isReglee() ? "En Cours" : "Annuler"));
            System.out.println("Articles : ");
            
            List<Article> articles = demandeDeDette.getArticles();
            if (articles != null) {
                for (Article article : articles) {
                    System.out.println("ID : " + article.getId());
                    System.out.println("Libellé : " + article.getLibelle());
                    System.out.println("Prix : " + article.getPrix());
                    System.out.println("Quantité : " + article.getQuantityInStock());
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("Aucun article associé à cette demande de dette.");
            }
    
            System.out.println("Valider ou refuser la demande de dette ? (1 pour Valider, 2 pour Refuser) : ");
            int choix = scanner.nextInt();
            scanner.nextLine();
    
            if (choix == 1) {
                demandeDeDette.setReglee(true);
                detteServiceImpl.mettreAJour(demandeDeDette);
                System.out.println("La demande de dette a été validée avec succès.");
            } else if (choix == 2) {
                demandeDeDette.setReglee(false);
                detteServiceImpl.mettreAJour(demandeDeDette);
                System.out.println("La demande de dette a été refusée avec succès.");
            }
        }
    }
    
    
    public Dette saisieDemandeDette() {
        Client client = findClient();  
        if (client == null) return null;
        List<Article> selectedArticles = selectArticles();  
        if (selectedArticles.isEmpty()) return null;
        double montantDemande = askMontantDemande();  
        Dette demandeDette = createDemandeDette(client, selectedArticles, montantDemande);
        detteServiceImpl.enregistrerDette(demandeDette);
        System.out.println("Demande de dette enregistrée avec succès pour le client " + client.getSurname());
    
        return demandeDette;
    }
    
    private double askMontantDemande() {
        double montantDemande = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("Entrez le montant de la dette demandée : ");
                montantDemande = Double.parseDouble(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un montant valide.");
            }
        }
        return montantDemande;
    }
    
    private Dette createDemandeDette(Client client, List<Article> selectedArticles, double montantDemande) {
        double montant = selectedArticles.stream().mapToDouble(Article::getPrix).sum();
    
        Dette demandeDette = new Dette();
        demandeDette.setClient(client);
        demandeDette.setMontant(montant);
        demandeDette.setMontantVerser(0);
        demandeDette.setReglee(false);
        demandeDette.setDate(LocalDateTime.now());
        demandeDette.setArticles(selectedArticles);
    
        System.out.println("Montant total de la demande de dette : " + montant);
        return demandeDette;
    }
    
    public void listerDemandesDeDetteClient() {
        Client client = findClient();  
        if (client == null) return;
    
        List<Dette> demandesDeDette = detteServiceImpl.findDemandesByClient(client);  
        if (demandesDeDette.isEmpty()) {
            System.out.println("Ce client n'a aucune demande de dette.");
        } else {
            afficherDemandesDeDette(demandesDeDette);
        }
    }
    
    private void afficherDemandesDeDette(List<Dette> demandesDeDette) {
        System.out.println("Liste des demandes de dette du client :");
        for (Dette demande : demandesDeDette) {
            System.out.println("ID: " + demande.getId() + ", Montant demandé: " + demande.getMontant() + ", Date: " + demande.getDate());
        }
    }
    
    }

