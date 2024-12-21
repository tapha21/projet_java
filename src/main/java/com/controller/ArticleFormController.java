package com.controller;

import com.entities.Article;
import com.services.ArticleServiceImpl;

import java.io.IOException;

import com.core.RepoInter.ArticleRepository;
import com.core.RepoInter.DetailsRepository;
import com.core.factory.Factory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ArticleFormController {

    @FXML
    private TextField reference;

    @FXML
    private TextField libelle;

    @FXML
    private TextField prix;

    @FXML
    private TextField quantite;

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;

    private final ArticleRepository articleRepository = Factory.getInstanceArticleRepository();
    private final DetailsRepository detailsRepository = Factory.getInstanceDetails();
    private final ArticleServiceImpl articleService = new ArticleServiceImpl(articleRepository, detailsRepository);

    @FXML
    public void initialize() {
        addButton.setOnAction(event -> addArticle());
        closeButton.setOnAction(event -> closeForm());
    }
    @FXML
    private void addArticle() {
        try {
            String ref = reference.getText().trim();
            String lib = libelle.getText().trim();
            String prixText = prix.getText().trim();
            String quantiteText = quantite.getText().trim();

            if (ref.isEmpty() || lib.isEmpty() || prixText.isEmpty() || quantiteText.isEmpty()) {
                System.err.println("Tous les champs doivent être remplis.");
                return;
            }

            double prx = Double.parseDouble(prixText);
            int quant = Integer.parseInt(quantiteText);

            Article newArticle = new Article();
            newArticle.setReference(ref);
            newArticle.setLibelle(lib);
            newArticle.setPrix(prx);
            newArticle.setQuantityInStock(quant);

            articleService.save(newArticle);

            reference.clear();
            libelle.clear();
            prix.clear();
            quantite.clear();

            System.out.println("Article ajouté avec succès !");
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format : " + e.getMessage());
        }
    }
    @FXML
    private void closeForm() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

     @FXML
    private void retour() {
        try {
            // Charger le fichier FXML de la liste des articles
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ArticleList.fxml"));
            AnchorPane articleListRoot = loader.load();

            // Créer une nouvelle scène avec la vue de la liste des articles
            Scene scene = new Scene(articleListRoot);

            // Récupérer la fenêtre actuelle (Stage) et l'appliquer à la nouvelle scène
            Stage stage = (Stage) articleListRoot.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur de chargement du FXML
        }
    }
}
