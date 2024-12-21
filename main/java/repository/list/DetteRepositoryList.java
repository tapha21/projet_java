package repository.list;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import core.RepoInter.DetteRepository;
import core.config.repoImpl.RepositoryImpl;
import entities.Article;
import entities.Client;
import entities.Dette;
public class DetteRepositoryList  extends RepositoryImpl<Dette> implements DetteRepository{
     private final List<Dette> listeDettes = new ArrayList<>();

    public void insert(Dette debt) {
        listeDettes.add(debt);
    }

    public List<Dette> selectAll() {
        return listeDettes;
    }

   public List<Dette> selectionnerReglees(boolean reglee) {
        return listeDettes.stream()
                          .filter(dette -> dette.estReglee() == reglee)
                          .collect(Collectors.toList());
    }

@Override
public Dette selectById(int id) {
    return listeDettes.stream()
    .filter(dette -> dette.getId() == id)
    .findFirst()
    .orElseThrow(() -> new NoSuchElementException("Aucune dette trouvée avec l'ID : " + id));
}

@Override
public void remove(int id) {
    Dette dette = selectById(id); // Utilise `selectById` pour obtenir la dette
    listeDettes.remove(dette);
    System.out.println("Dette avec l'ID " + id + " supprimée avec succès.");
}

@Override
public Dette findby(int id) {
    return listeDettes.stream()
    .filter(dette -> dette.getId() == id)
    .findFirst()
    .orElseThrow(() -> new NoSuchElementException("Aucune dette trouvée avec l'ID : " + id));
}

@Override
public void selectByTel(String tel) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'selectByTel'");
}

@Override
public void save(Dette dette) {
    listeDettes.add(dette);
}

@Override
public Dette findById(int id) {
    return list.stream()
                   .filter(dette -> dette.getId() == id)
                   .findFirst()
                   .orElse(null);
}

@Override
public List<Dette> findByClient(Client client) {
    return listeDettes.stream()
    .filter(dette -> dette.getClient() == client)
    .collect(Collectors.toList());
}

@Override
public void update(Dette dette) {
    Dette existingDette = selectById(dette.getId());
    existingDette.setMontant(dette.getMontant());
    existingDette.setMontantVerser(dette.getMontantVerser());
    existingDette.setReglee(dette.estReglee());
    existingDette.setClient(dette.getClient());
    existingDette.setDate(dette.getDate());
    System.out.println("Dette avec l'ID " + dette.getId() + " mise à jour avec succès.");
}

@Override
public List<Dette> NonSolde(Client client) {
    return listeDettes.stream()
    .filter(dette -> !dette.estReglee() && dette.getClient().equals(client))
    .collect(Collectors.toList());

}
public List<Dette> findByEtat(boolean etat) {
    return listeDettes.stream()
            .filter(dette -> dette.isReglee() == etat)
            .collect(Collectors.toList());
}

}
