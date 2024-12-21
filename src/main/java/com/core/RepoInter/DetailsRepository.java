package com.core.RepoInter;

import java.util.List;

import com.core.config.Repository;
import com.entities.Article;
import com.entities.Detail;
import com.entities.Dette;
import com.entities.Paiement;

public interface DetailsRepository extends Repository<Detail> {
    List<Article>findByDette(Dette dette);
    List<Paiement>findDette(Dette dette);

}
