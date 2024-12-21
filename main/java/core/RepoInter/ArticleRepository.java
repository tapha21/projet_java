package core.RepoInter;

import java.util.List;

import core.config.Repository;
import entities.Article;
import entities.Dette;

public interface ArticleRepository extends Repository<Article> {
    void selectByTel(String tel);
    void save(Article article);
    Article findById(int id);
    void update(Article article);
    void updateQuantite(Article article);
    void activerArticle(Article article);
    void desactiverArticle(Article article);
    List<Article> getDesactiverArticle();
}
