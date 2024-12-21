package com.repository.jpa;

import java.util.List;

import com.core.RepoInter.ArticleRepository;
import com.core.config.repoImpl.RepositoryJpaImpl;
import com.entities.Article;

public class ArticleJPA extends RepositoryJpaImpl<Article> implements ArticleRepository {
    public ArticleJPA() {
        super(Article.class);
    }
    @Override
    public void selectByTel(String tel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectByTel'");
    }

    @Override
    public void save(Article article) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Article findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public void updateQuantite(Article article) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateQuantite'");
    }

    @Override
    public void activerArticle(Article article) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'activerArticle'");
    }

    @Override
    public void desactiverArticle(Article article) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'desactiverArticle'");
    }

    @Override
    public List<Article> getDesactiverArticle() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDesactiverArticle'");
    }

    @Override
    public List<Article> findByLibelle(String libelle) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByLibelle'");
    }
    
}
