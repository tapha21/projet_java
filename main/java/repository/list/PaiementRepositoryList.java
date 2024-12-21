package repository.list;

import core.RepoInter.PaiementRepository;
import core.config.repoImpl.RepositoryImpl;
import entities.Paiement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaiementRepositoryList extends RepositoryImpl<Paiement> implements PaiementRepository {
    
    private List<Paiement> paiements = new ArrayList<>();

    @Override
    public void update(Paiement objet) {
        Paiement paiementExistant = selectById(objet.getId());
        if (paiementExistant != null) {
            paiementExistant.setId(objet.getId());
            paiementExistant.setMontant(objet.getMontant());
            paiementExistant.setDate(objet.getDate());
            paiementExistant.setDette(objet.getDette());
        } else {
            throw new UnsupportedOperationException("Le paiement avec cet ID n'existe pas.");
        }
    }

    @Override
    public Paiement selectById(int id) {
        Optional<Paiement> paiement = paiements.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        return paiement.orElse(null);
    }

    @Override
    public void remove(int id) {
        Paiement paiement = selectById(id);
        if (paiement != null) {
            paiements.remove(paiement);
        } else {
            throw new UnsupportedOperationException("Le paiement avec cet ID n'existe pas.");
        }
    }

    @Override
    public Paiement findby(int id) {
        return selectById(id);
    }

    @Override
    public void save(Paiement paiement) {
        if (paiement.getId() == 0) {
            paiement.setId(generateNewId());
        }
        paiements.add(paiement);
    }

    @Override
    public Paiement findById(int id) {
        return selectById(id);
    }

    private int generateNewId() {
        if (paiements.isEmpty()) {
            return 1;
        } else {
            return paiements.stream().mapToInt(Paiement::getId).max().orElse(0) + 1;
        }
    }

    @Override
    public List<Paiement> findByDette(int detteId) {
        return paiements.stream()
                .filter(p -> p.getDette().getId() == detteId)
                .collect(Collectors.toList());
    }
}
