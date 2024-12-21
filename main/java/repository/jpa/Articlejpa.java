package repository.jpa;

import java.util.List;
import core.RepoInter.ArticleRepository;
import core.config.repoImpl.RepositoryJpaImpl;
import entities.Article;

public class Articlejpa extends RepositoryJpaImpl<Article> implements ArticleRepository {
    public Articlejpa() {
        super(Article.class);
    }

    @Override
    public void selectByTel(String tel) {
        throw new UnsupportedOperationException("Méthode 'selectByTel' non implémentée");
    }

    @Override
    public Article findById(int id) {
        return this.selectById(id);
    }

    @Override
    public void updateQuantite(Article article) {
        this.update(article);
    }

    @Override
    public void activerArticle(Article article) {
        article.setEtat(true);
        this.update(article);
    }

    @Override
    public void desactiverArticle(Article article) {
        article.setEtat(false);
        this.update(article);
    }

    @Override
    public List<Article> getDesactiverArticle() {
        String jpql = "SELECT a FROM Article a WHERE a.etat = false";
        return em.createQuery(jpql, Article.class).getResultList();
    }

    @Override
    public void save(Article article) {
        // if (article.getId() == null) {
        //     this.insert(article);
        // } else {
        //     this.update(article);
        // }
    }
}
