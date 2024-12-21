package core.RepoInter;

import java.util.List;

import core.config.Repository;
import entities.Article;
import entities.Detail;
import entities.Dette;
import entities.Paiement;

public interface DetailsRepository extends Repository<Detail> {
    List<Article>findByDette(Dette dette);
    List<Paiement>  findDette(Dette dette);

}
