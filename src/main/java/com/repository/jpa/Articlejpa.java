package com.repository.jpa;

import java.util.List;
import com.core.RepoInter.ArticleRepository;
import com.core.config.repoImpl.RepositoryJpaImpl;
import com.entities.Article;

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

    @Override
    public List<Article> findByLibelle(String libelle) {
        try {
            return em.createQuery("SELECT a FROM Article a WHERE a.libelle = :libelle", Article.class)
                     .setParameter("libelle", libelle)
                     .getResultList();  // Utiliser getResultList pour récupérer plusieurs résultats
        } catch (Exception e) {
            e.printStackTrace();  // Ajoutez un affichage d'erreur pour mieux comprendre ce qui se passe
            return null;  // Retourner null en cas d'erreur
        }
    }
}
