package repository.list;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import core.RepoInter.ArticleRepository;
import core.config.repoImpl.RepositoryImpl;
import entities.Article;
import entities.Dette;

public class ArticleRepositoryList extends RepositoryImpl<Article> implements ArticleRepository{
    private final List<Article> articleList = new ArrayList<>();

    public void insert(Article article) {
        articleList.add(article);
    }

    public List<Article> selectAll() {
        return articleList;
    }

    public List<Article> selectAvailable() {
        return articleList.stream()
                .filter(article -> article.getQuantityInStock() > 0)
                .collect(Collectors.toList());
    }

    public void updateQuantity(String name, int quantity) {
        for (Article article : articleList) {
            if (article.getLibelle().equals(name)) {
                article.setQuantityInStock(quantity);
                break;
            }
        }
    }

    @Override
    public Article selectById(int id) {
        return articleList.stream()
                .filter(article -> article.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void remove(int id) {
        articleList.removeIf(article -> article.getId() == id);
    }

    @Override
    public Article findby(int id) {
        return selectById(id);
    }

    @Override
    public void updateQuantite(Article article) {
        for (Article a : articleList) {
            if (a.getId() == article.getId()) {
                a.setQuantityInStock(article.getQuantityInStock());
                break;
            }
        }
    }

    @Override
    public void activerArticle(Article article) {
        for (Article a : articleList) {
            if (a.getId() == article.getId()) {
                a.setEtat(true);
                break;
            }
        }
    }

    @Override
    public void desactiverArticle(Article article) {
        for (Article a : articleList) {
            if (a.getId() == article.getId()) {
                a.setEtat(false);
                break;
            }
        }
    }

    @Override
    public List<Article> getDesactiverArticle() {
        return articleList.stream()
                .filter(article -> !article.isEtat())
                .collect(Collectors.toList());
    }

    @Override
    public void update(Article article) {
        for (Article a : articleList) {
            if (a.getId() == article.getId()) {
                a.setLibelle(article.getLibelle());
                a.setPrix(article.getPrix());
                a.setQuantityInStock(article.getQuantityInStock());
                a.setEtat(article.isEtat());
                break;
            }
        }
    }

    @Override
    public Article findById(int id) {
        return articleList.stream()
        .filter(article -> article.getId() == id)
        .findFirst()
        .orElse(null);
    }

    

    @Override
    public void selectByTel(String tel) {
        throw new UnsupportedOperationException("Unimplemented method 'selectByTel'");
    }

    @Override
    public void save(Article article) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }


}