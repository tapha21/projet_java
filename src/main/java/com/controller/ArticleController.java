package com.controller;

import java.io.IOException;

import com.App;
import com.core.RepoInter.ArticleRepository;
import com.core.RepoInter.DetailsRepository;
import com.entities.Article;
import com.services.ArticleServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.core.factory.Factory;
import javafx.scene.Parent;  
import javafx.stage.Stage;

public class ArticleController {
    @FXML
    private Button articlesButton;

    @FXML
    private Button dettesButton;

    @FXML
    private Button clientsButton;

    @FXML
    private Button usersButton;
    @FXML
    private Button ADDBTN;
    @FXML
    private Button creer;
    @FXML
    private Button returnbtn;
    @FXML
    private TextField libelle;

    @FXML
    private TextField prix;

    @FXML
    private TextField quantite;

    @FXML
    private TextField reference;

    @FXML
    private TableView<Article> articleTable;
    @FXML
    private TableColumn<Article, Integer> IDColumn;
    @FXML
    private TableColumn<Article, String> refColumn;
    @FXML
    private TableColumn<Article, String> libelleColumn;
    @FXML
    private TableColumn<Article, Integer> qteStockColumn;
    @FXML
    private TableColumn<Article, Double> prixColumn;
    @FXML
    private ComboBox<String> filterComboBox;

ArticleRepository articleRepository = Factory.getInstanceArticleRepository();
DetailsRepository detailsRepository = Factory.getInstanceDetails();
ArticleServiceImpl articleService = new ArticleServiceImpl(articleRepository, detailsRepository);
    @FXML
    public void initialize() {
        try {
            IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            refColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
            libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            qteStockColumn.setCellValueFactory(new PropertyValueFactory<>("quantityInStock")); 
            prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
            listArticles(); 
            filterComboBox.getItems().addAll("All", "Available");
            filterComboBox.getSelectionModel().select("All");
            filterComboBox.setOnAction(event -> filterArticles());
            ADDBTN.setOnAction(event -> addArticle());
            dettesButton.setOnAction(event -> loadPage("Dettes.fxml"));
            articlesButton.setOnAction(event -> loadPage("Articles.fxml"));
            clientsButton.setOnAction(event -> loadPage("Client.fxml"));
            usersButton.setOnAction(event -> loadPage("Users.fxml"));
              // creer.setOnAction(event -> showCreationForm());
              // returnbtn.setOnAction(event -> loadPage("Articles.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        }
    

    private void listArticles() {
        ObservableList<Article> articleList = FXCollections.observableArrayList(articleService.find());
        System.out.println("Articles trouvés : " + articleList.size());
        articleTable.setItems(articleList);
    }

    @FXML
    private void filterArticles() {
        String selectedFilter = filterComboBox.getSelectionModel().getSelectedItem();
        ObservableList<Article> filteredArticles = FXCollections.observableArrayList();
        ObservableList<Article> allArticles = FXCollections.observableArrayList(articleService.find());

        if ("Available".equals(selectedFilter)) {
            for (Article article : allArticles) {
                if (article.getQuantityInStock() > 0) {
                    filteredArticles.add(article);
                }
            }
            articleTable.setItems(filteredArticles);
        } else {
            articleTable.setItems(allArticles);
        }
    }
    @FXML
private void addArticle() {
    try {
        String ref = reference.getText().trim();
        String lib = libelle.getText().trim();
        String prixText = prix.getText().trim();
        String quantiteText = quantite.getText().trim();

        // Vérifiez que les champs ne sont pas vides
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

        // Enregistrez l'article
        articleService.save(newArticle);
        
        // Effacez les champs après l'ajout
        reference.clear();
        libelle.clear();
        prix.clear();
        quantite.clear();
        
        // Mettez à jour la liste des articles
        listArticles();
        System.out.println("Article ajouté avec succès!");

    } catch (NumberFormatException e) {
        System.err.println("Erreur de format des champs prix ou quantité : " + e.getMessage());
    } catch (Exception e) {
        System.err.println("Erreur lors de l'ajout de l'article : " + e.getMessage());
    }
}
    @FXML
    private void showCreationForm() {
        App.switchToView("/com/SaveArticles.fxml");
        
    }
    @FXML
    private void retour() {
        App.switchToView("/com/Article.fxml");
    }
    @FXML
    private void closeArticleForm() {
        Stage stage = (Stage) creer.getScene().getWindow(); // Obtenez la scène à partir du bouton 'creer'
        stage.close(); // Ferme la fenêtre actuelle
    }

    private void loadPage(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/" + fxmlFile));
            Scene scene = new Scene(root);
            Stage stage = (Stage) articlesButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gestion des erreurs à améliorer
        }
    }
}