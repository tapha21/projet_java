package services;

import java.util.List;

import core.RepoInter.DetteRepository;
import entities.Client;
import entities.Dette;
import entities.User;
public class DetteServiceImpl implements DetteService {
    ArticleServiceImpl articleService;
    ClientService clientService;
    private static int clientIdCounter = 1;
    private DetteRepository detteRepository;
    public DetteServiceImpl(DetteRepository detteRepository, ArticleServiceImpl articleService, ClientService clientService) {
        this.detteRepository = detteRepository;
        this.articleService = articleService;
        this.clientService = clientService;
    }
    public List<Dette> list() {
        return detteRepository.selectAll();
    }

    @Override
    public void createDette(Dette dette) {
       
    }

    @Override
    public List<Dette> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Client search(String tel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    public DetteServiceImpl(DetteRepository detteRepository) {
        this.detteRepository = detteRepository;
    }

    public void enregistrerDette(Dette dette) {
        dette.setId(clientIdCounter++);
        detteRepository.insert(dette);
    }

    public Dette findById(int id) {
        return detteRepository.findby(id);
    }

    public List<Dette> findByClient(Client client) {
        return detteRepository.findByClient(client);
    }

    public void mettreAJour(Dette dette) {
        detteRepository.update(dette);
    }
    public List<Dette> solde( Client client){
        return detteRepository.NonSolde(client);
    }

      public void incrementDetteIds(List<Dette> dette) {
        int idCounter = 1; 
        for (Dette dette2 : dette) {
            dette2.setId(idCounter); 
            idCounter++; 
        }
    }
    public List<Dette> findByEtat(boolean etat) {
        return detteRepository.findByEtat(etat);
    }
    public void archiverDettesSoldées() {
        List<Dette> dettesSoldées = findByEtat(false);
        for (Dette dette : dettesSoldées) {
            if(dette.isReglee()){
                dette.setReglee(false);
                detteRepository.update(dette);
            }
        }
        System.out.println("Les dettes soldées ont été archivées avec succès.");
    }
    public List<Dette> findDemandesByClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client invalide ou non trouvé.");
        }
        return detteRepository.findByClient(client);
    }
}

    

