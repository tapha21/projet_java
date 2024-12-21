package views;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import core.RepoInter.View;
import entities.Article;
import entities.Client;
import entities.Dette;
import services.ArticleServiceImpl;
import services.DetteServiceImpl;

public class ArticleVue implements View<Article> {
    private final Scanner scanner = new Scanner(System.in);
    private final ArticleServiceImpl articleServiceImpl;
    private final DetteServiceImpl detteServiceImpl;


    public ArticleVue(ArticleServiceImpl articleServiceImpl , DetteServiceImpl detteServiceImpl) {
        this.articleServiceImpl = articleServiceImpl;
        this.detteServiceImpl = detteServiceImpl;
    }

    @Override
    public Article saisie() {
        Article article = new Article();
        System.out.println("Entrer le libellé de l'article");
        article.setLibelle(scanner.nextLine());
        System.out.println("Entrer le prix de l'article");
        article.setPrix(scanner.nextDouble());
        scanner.nextLine(); 
        System.out.println("Entrer la quantité en stock de l'article");
        article.setQuantityInStock(scanner.nextInt());
        scanner.nextLine(); 
        System.out.println("Entrer la référence de l'article");
        article.setReference(scanner.nextLine());
        article.setEtat(true);
        article.setCreatAt(LocalDateTime.now());
        article.setUpdateAt(LocalDateTime.now());
        articleServiceImpl.save(article);
        return article;
    }

    @Override
    public void afficher(List<Article> list) {
        for (Article article : list) {
            System.out.println("ID : " + article.getId());
            System.out.println("Libellé : " + article.getLibelle());
            System.out.println("Prix : " + article.getPrix());
            System.out.println("Quantité en stock : " + article.getQuantityInStock());
            System.out.println("Référence : " + article.getReference());
            System.out.println("Date de création : " + article.getCreatAt());
            System.out.println("Date de mise à jour : " + article.getUpdateAt());
            System.out.println("------------------------");
        }
    }

     public void affiche() {
       List<Article> articlesDisponibles = articleServiceImpl.find().stream()
            .filter(article -> article.getQuantityInStock() > 0)
            .collect(Collectors.toList());
        for (Article article : articlesDisponibles) {
            System.out.println("ID : " + article.getId());
            System.out.println("Libellé : " + article.getLibelle());
            System.out.println("Prix : " + article.getPrix());
            System.out.println("Quantité en stock : " + article.getQuantityInStock());
            System.out.println("------------------------");
        }
    }

    public void listerArticleDette() {
    System.out.println("Entrez l'id du dettes : ");
    int id = scanner.nextInt();
    Dette dette = detteServiceImpl.findById(id);
    if (dette == null) {
        System.out.println("cette dette n'existe pas !");
        return;
    }
    List<Article> articles = articleServiceImpl.findByDette(dette);
    if (articles.isEmpty()) {
        System.out.println("Cette dette n'as pas d'articles.");
    } else {
        afficher(articles); 
    }
}

public void activerArticle() {
    List<Article> articleList = articleServiceImpl.find();
    System.out.println("Entrez l'id de l'article que vous souhaitez activer : ");
    int id = Integer.parseInt(scanner.nextLine());
    Article article = articleServiceImpl.findById(id);
    if (article != null) {
        articleServiceImpl.activerArticle(article);
        System.out.println("L'article a été activé avec succès.");
    } else {
        System.out.println("L'article n'existe pas.");
    }
}
public void listerArticleDesactive() {
    List<Article> articlesDesactives = articleServiceImpl.getDesactiverArticle();
    if (articlesDesactives.isEmpty()) {
        System.out.println("Aucun article désactivé.");
    } else {
        for (Article article : articlesDesactives) {
            System.out.println("ID : " + article.getId());
            System.out.println("Libellé : " + article.getLibelle());
            System.out.println("Prix : " + article.getPrix());
            System.out.println("Quantité en stock : " + article.getQuantityInStock());
            System.out.println("État : " + (article.isEtat() ? "Activé" : "Désactivé"));
            System.out.println();
        }
    }
}
public void mettreAJourQuantiteEnStock() {
    System.out.println("Entrez l'ID de l'article que vous souhaitez mettre à jour : ");
    int id = Integer.parseInt(scanner.nextLine());
    Article article = articleServiceImpl.findById(id);
    if (article != null) {
        System.out.println("Entrez la nouvelle quantité en stock : ");
        int nouvelleQuantite = Integer.parseInt(scanner.nextLine());
        article.setQuantityInStock(nouvelleQuantite);
        articleServiceImpl.update(article);
        System.out.println("La quantité en stock de l'article a été mise à jour avec succès.");
    } else {
        System.out.println("L'article n'existe pas.");
    }
}
}