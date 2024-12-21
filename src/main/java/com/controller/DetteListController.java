package com.controller;

import com.core.factory.Factory;
import com.core.RepoInter.ArticleRepository;
import com.core.RepoInter.DetailsRepository;
import com.core.RepoInter.DetteRepository;
import com.entities.Article;
import com.entities.Dette;
import com.services.ArticleServiceImpl;
import com.services.ClientService;
import com.services.ClientServiceImpl;
import com.services.DetteServiceImpl;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class DetteListController {

    @FXML
    private TableView<Dette> detteTable;

    @FXML
    private TableColumn<Dette, String> date;
    @FXML
    private TableColumn<Dette, Double> montant;
    @FXML
    private TableColumn<Dette, Double> montantRestant;
    @FXML
    private TableColumn<Dette, String> client;
    @FXML
    private TableColumn<Dette, Boolean> reglee;
    @FXML
    private TableColumn<Dette, String> details;
    @FXML
    private Button articlesButton;
    
    @FXML
    private Button dettesButton;
    
    @FXML
    private Button clientsButton;
    
    @FXML
    private Button usersButton;

    @FXML
    private ComboBox<String> clientComboBox;

    @FXML
    private ComboBox<Article> article;

    
    @FXML
    private TextField filterField;

    @FXML
    private Button creerdettes;
    @FXML
    private ComboBox<String> filterComboBox;

    private DetteServiceImpl detteService;

    @FXML
    public void initialize() {
        try {
            ArticleRepository articleRepository = Factory.getInstanceArticleRepository();
            DetailsRepository detailsRepository = Factory.getInstanceDetails();
            DetteRepository detteRepository = Factory.getInstanceDettesRepository();
            ClientService clientService = new ClientServiceImpl(Factory.getInstanceClientRepository());
            ArticleServiceImpl articleService = new ArticleServiceImpl(articleRepository, detailsRepository);
            this.detteService = new DetteServiceImpl(detteRepository, articleService, clientService);
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            montant.setCellValueFactory(new PropertyValueFactory<>("montant"));
            montantRestant.setCellValueFactory(dette -> {
                Dette detteObject = dette.getValue();
                double montant = detteObject.getMontant();         
                double montantVerse = detteObject.getMontantVerser();  
                return new SimpleDoubleProperty(montant - montantVerse).asObject();
            });      
                  client.setCellValueFactory(dette -> {
                Dette detteObject = dette.getValue();
                return new SimpleStringProperty(detteObject.getClient().getSurname()); 
            });          
            reglee.setCellValueFactory(new PropertyValueFactory<>("reglee"));
            details.setCellValueFactory(new PropertyValueFactory<>("details"));
            loadAllDettes();
            setupTableWithFilter();
            filterComboBox.getItems().addAll("Tous", "Reglee", "Non_Reglee");
            filterComboBox.getSelectionModel().select("Tous");
            filterComboBox.setOnAction(event -> filterDette());
            filterComboBox.setOnAction(event -> filterDette());
            articlesButton.setOnAction(event -> loadPage("Articles.fxml"));
            dettesButton.setOnAction(event -> loadPage("Dettes.fxml"));
            clientsButton.setOnAction(event -> loadPage("Client.fxml"));
            usersButton.setOnAction(event -> loadPage("Users.fxml"));
            creerdettes.setOnAction(event -> loadPage("DetteForm.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void filterDette() {
        String selectedFilter = filterComboBox.getSelectionModel().getSelectedItem();
        List<Dette> dettes = detteService.findAll(); // Récupérer toutes les dettes
    
        // Créer un FilteredList à partir de la liste des dettes
        FilteredList<Dette> filteredData = new FilteredList<>(FXCollections.observableArrayList(dettes), b -> true);
    
        filteredData.setPredicate(dette -> {
            // Si aucun filtre n'est sélectionné ou si "Tous" est sélectionné, afficher toutes les dettes
            if (selectedFilter == null || selectedFilter.isEmpty() || selectedFilter.equals("Tous")) {
                return true;
            }
    
            // Si "Reglee" est sélectionné, filtrer les dettes réglées
            if (selectedFilter.equals("Reglee")) {
                return dette.isReglee(); // Retourne les dettes réglées
            }
            // Si "Non_Reglee" est sélectionné, filtrer les dettes non réglées
            if (selectedFilter.equals("Non_Reglee")) {
                return !dette.isReglee(); // Retourne les dettes non réglées
            }
    
            return false;
        });
    
        // Trier et afficher les données filtrées dans le TableView
        SortedList<Dette> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(detteTable.comparatorProperty());
        detteTable.setItems(sortedData); // Afficher la liste filtrée dans le TableView
    }
    
    private void setupTableWithFilter() {
        try {
            List<Dette> dettes = detteService.findAll();
            FilteredList<Dette> filteredData = new FilteredList<>(FXCollections.observableArrayList(dettes), b -> true);
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(dette -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return dette.getClient().getSurname().toLowerCase().contains(lowerCaseFilter);                });
            });
            SortedList<Dette> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(detteTable.comparatorProperty());
            detteTable.setItems(sortedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllDettes() {
        try {
            List<Dette> dettes = detteService.findAll();
            detteTable.getItems().setAll(dettes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPage(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/" + fxmlFile));
            Scene scene = new Scene(root);
            Stage stage = (Stage) creerdettes.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     
    
}
