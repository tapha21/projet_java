package com.core.RepoInter;

import java.util.List;

import com.core.config.Repository;
import com.entities.Article;

public interface ArticleRepository extends Repository<Article> {
    void selectByTel(String tel);
    void save(Article article);
    Article findById(int id);
    void update(Article article);
    void updateQuantite(Article article);
    void activerArticle(Article article);
    void desactiverArticle(Article article);
    List<Article> getDesactiverArticle();
    List<Article> findByLibelle(String libelle);

}
