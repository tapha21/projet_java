package com.controller;

import com.core.RepoInter.ArticleRepository;
import com.core.RepoInter.DetailsRepository;
import com.core.RepoInter.DetteRepository;
import com.core.factory.Factory;
import com.entities.Article;
import com.entities.Client;
import com.entities.Dette;
import com.services.ArticleServiceImpl;
import com.services.ClientService;
import com.services.ClientServiceImpl;
import com.services.DetteServiceImpl;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class DetteFormController {

    @FXML
    private TextField montantverser;

    @FXML
    private ComboBox<String> article;

    @FXML
    private ComboBox<String> clientComboBox;

    @FXML
    private DatePicker dateField;

    @FXML
    private Button saveButton;

    @FXML
    private Button returnButton;

    @FXML
    private TableView<Article> articletable;

    @FXML
    private TableColumn<Article, String> libart;

    @FXML
    private TableColumn<Article, String> prixart;

    @FXML
    private TableColumn<Article, String> quantiteart;

    @FXML
    private Button validArt;

  
    @FXML
    private TextField quantite;

    private DetteServiceImpl detteService;

    private ObservableList<Article> selectedArticles = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ArticleRepository articleRepository = Factory.getInstanceArticleRepository();
        DetailsRepository detailsRepository = Factory.getInstanceDetails();
        DetteRepository detteRepository = Factory.getInstanceDettesRepository();
        ClientService clientService = new ClientServiceImpl(Factory.getInstanceClientRepository());
        ArticleServiceImpl articleService = new ArticleServiceImpl(articleRepository, detailsRepository);
        this.detteService = new DetteServiceImpl(detteRepository, articleService, clientService);

        try {
            loadClients();
            loadArticles();
            validArt.setOnAction(event -> addArticleToTable());
            saveButton.setOnAction(event -> saveDette());
            returnButton.setOnAction(event -> returnbtn());
            libart.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            prixart.setCellValueFactory(new PropertyValueFactory<>("prix"));
            quantiteart.setCellValueFactory(new PropertyValueFactory<>("quantityInStock"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addArticleToTable() {
        String articleLibelle = article.getValue();
        String quantiteText = quantiteart.getText();

        if (articleLibelle == null || quantiteText.isEmpty()) {
            System.out.println("Veuillez sélectionner un article et saisir une quantité.");
            return;
        }

        int quantiteValue;
        try {
            quantiteValue = Integer.parseInt(quantiteText);
        } catch (NumberFormatException e) {
            System.out.println("Veuillez saisir une quantité valide.");
            return;
        }

        // Chercher l'article par son libellé
        List<Article> selectedArticlesList = Factory.getInstanceArticleRepository().findByLibelle(articleLibelle);
        if (!selectedArticlesList.isEmpty()) {
            Article selectedArticle = selectedArticlesList.get(0);

            // Vérifiez si l'article existe déjà dans la table
            boolean articleExist = false;
            for (Article articleInTable : selectedArticles) {
                if (articleInTable.getLibelle().equals(selectedArticle.getLibelle())) {
                    // Si l'article existe déjà, mettez à jour la quantité
                    articleInTable.setQuantityInStock(quantiteValue);
                    articleExist = true;
                    break;
                }
            }

            if (!articleExist) {
                selectedArticle.setQuantityInStock(quantiteValue);
                selectedArticles.add(selectedArticle);
            }

            articletable.setItems(selectedArticles);
        }
    }

    private void loadClients() {
        List<Client> clientList = Factory.getInstanceClientRepository().selectAll();
        ObservableList<String> clientNames = FXCollections.observableArrayList();

        for (Client client : clientList) {
            clientNames.add(client.getSurname());
        }

        clientComboBox.setItems(clientNames);
    }

    private void loadArticles() {
        List<Article> articleList = Factory.getInstanceArticleRepository().selectAll();
        ObservableList<String> articleNames = FXCollections.observableArrayList();

        for (Article article : articleList) {
            articleNames.add(article.getLibelle());
        }

        this.article.setItems(articleNames);
    }

    private void saveDette() {
        try {
            if (montantverser != null && !montantverser.getText().isEmpty()) {
                String montant = montantverser.getText();
            } else {
                System.out.println("montantverser is null or empty");
                return;
            }

            String clientSurname = clientComboBox.getValue();
            if (clientSurname == null) {
                System.out.println("Client not selected");
                return;
            }

            String articleLibelle = this.article.getValue();
            if (articleLibelle == null) {
                System.out.println("Article not selected");
                return;
            }

            Client selectedClient = Factory.getInstanceClientRepository().selectBySurname(clientSurname);
            List<Article> selectedArticles = Factory.getInstanceArticleRepository().findByLibelle(articleLibelle);

            Dette nouvelleDette = new Dette();
            nouvelleDette.setMontant(Double.parseDouble(montantverser.getText()));
            nouvelleDette.setClient(selectedClient);
            nouvelleDette.setArticles(selectedArticles);
            detteService.createDette(nouvelleDette);

            returnbtn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void returnbtn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/Dettes.fxml"));
            AnchorPane clientListRoot = loader.load();
            Scene scene = new Scene(clientListRoot);
            Stage stage = (Stage) clientListRoot.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
}
