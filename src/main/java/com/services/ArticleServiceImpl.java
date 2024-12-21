package com.services;

import java.util.List;

import com.core.RepoInter.ArticleRepository;
import com.core.RepoInter.DetailsRepository;
import com.core.RepoInter.DetteRepository;
import com.core.RepoInter.Service;
import com.core.factory.Factory;
import com.entities.Article;
import com.entities.Client;
import com.entities.Dette;

public class ArticleServiceImpl implements Service<Article>{
    private ArticleRepository articleRepository =Factory.getInstanceArticleRepository();
    private static int clientIdCounter = 1;
    private DetailsRepository detailsRepository = Factory.getInstanceDetails();
    public ArticleServiceImpl(ArticleRepository articleRepositori, DetailsRepository detailsRepository) {
        this.articleRepository = articleRepositori;
        this.detailsRepository = detailsRepository;
    }
    @Override
    public void save(Article objet) {
        objet.setId(clientIdCounter++);
        articleRepository.insert(objet);

    }

    @Override
    public List<Article> find() {
        return articleRepository.selectAll();
    }

    @Override
    public void update(Article objet) {
        articleRepository.update(objet);

    }
     public List<Article> findByDette(Dette dette) {
        return detailsRepository.findByDette(dette);
    }

    public void updateQuantite(Article article) {
        articleRepository.updateQuantite(article);
    }

    public void activerArticle(Article article) {
        articleRepository.activerArticle(article);
    }

    // private ArticleRepository articleRepository=new ArticleRepository();

   public void incrementIds(List<Article> articles) {
        int idCounter = 1; 

        for (Article article : articles) {
            article.setId(idCounter); 
            idCounter++; 
        }
    }
    public Article findById(int id) {
        return articleRepository.findById(id);
    }

    public List<Article> getDesactiverArticle() {
        return articleRepository.getDesactiverArticle();
    }
    
    public List<Article> getAllArticles() {
        return articleRepository.selectAll();
    }

    public List<Article> getArticleByLibelle(String libelle) {
        return articleRepository.findByLibelle(libelle);  
        }
}