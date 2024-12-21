package repository.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import core.RepoInter.DetailsRepository;
import core.config.repoImpl.RepositoryImpl;
import entities.Article;
import entities.Detail;
import entities.Dette;
import entities.Paiement;

public class DetailRepositoryList extends RepositoryImpl<Detail> implements DetailsRepository {

    private List<Detail> details = new ArrayList<>();

    @Override
    public void update(Detail objet) {
        Optional<Detail> detailOpt = details.stream()
            .filter(detail -> detail.getId() == objet.getId())
            .findFirst();

        if (detailOpt.isPresent()) {
            Detail detailToUpdate = detailOpt.get();
            detailToUpdate.setArticle(objet.getArticle());
            detailToUpdate.setQuantite(objet.getQuantite());
            detailToUpdate.setPrixVente(objet.getPrixVente());
            System.out.println("Détail mis à jour avec succès.");
        } else {
            System.out.println("Détail non trouvé pour la mise à jour.");
        }
    }

    @Override
    public Detail selectById(int id) {
        return details.stream()
            .filter(detail -> detail.getId() == id)
            .findFirst()
            .orElse(null);
    }

    @Override
    public void remove(int id) {
        Optional<Detail> detailOpt = details.stream()
            .filter(detail -> detail.getId() == id)
            .findFirst();

        if (detailOpt.isPresent()) {
            details.remove(detailOpt.get());
            System.out.println("Détail supprimé avec succès.");
        } else {
            System.out.println("Détail non trouvé pour la suppression.");
        }
    }

    @Override
    public Detail findby(int id) {
        return selectById(id);
    }

    @Override
    public List<Article> findByDette(Dette dette) {
        if (dette != null) {
            if (dette.getArticles() != null) {
                System.out.println("Articles trouvés pour la dette : " + dette.getArticles().size());
                return dette.getArticles();
            } else {
                System.out.println("Aucun article associé à cette dette.");
            }
        } else {
            System.out.println("Dette est null.");
        }
        return new ArrayList<>();
    }

    @Override
    public List<Paiement> findDette(Dette dette) {
        if (dette != null) {
            if (dette.getPaiements() != null) {
                System.out.println("Paiements trouvés pour la dette : " + dette.getPaiements().size());
                return dette.getPaiements();
            } else {
                System.out.println("Aucun paiement associé à cette dette.");
            }
        } else {
            System.out.println("Dette est null.");
        }
        return new ArrayList<>();
    }
}
