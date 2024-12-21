package com.controller;
import javafx.collections.FXCollections;
import com.core.RepoInter.ArticleRepository;
import com.core.RepoInter.DetailsRepository;
import com.core.RepoInter.DetteRepository;
import com.core.factory.Factory;
import com.entities.Dette;
import com.entities.User;
import com.services.ArticleServiceImpl;
import com.services.ClientService;
import com.services.ClientServiceImpl;
import com.services.DetteServiceImpl;
import com.services.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class DetteController {

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
    private ComboBox<String> filterComboBox;


    private DetteServiceImpl detteService;
    private UserServiceImpl userService;
    private User currentUser;

    @FXML
    public void initialize() {
        DetteRepository detteRepository = Factory.getInstanceDettesRepository();
        ArticleRepository articleRepository = Factory.getInstanceArticleRepository();
        DetailsRepository detailsRepository = Factory.getInstanceDetails();
        ClientService clientService = new ClientServiceImpl(Factory.getInstanceClientRepository());
        userService = new UserServiceImpl(Factory.getInstanceUserRepository());

        ArticleServiceImpl articleService = new ArticleServiceImpl(articleRepository, detailsRepository);

        this.detteService = new DetteServiceImpl(detteRepository, articleService, clientService);

        // Initialiser les colonnes du tableau
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

        // Charger les dettes pour l'utilisateur connecté
        loadDettesForUser();
    }

   private void loadDettesForUser() {
       List<User> users = userService.findAll();
       if (!users.isEmpty()) {
           User currentUser = users.get(0);
           ObservableList<Dette> dettes = FXCollections.observableArrayList(detteService.getDettesByClient(currentUser));        
           detteTable.setItems(dettes);
       }
   }


    @FXML
    private void handleViewDetails(MouseEvent event) {
        Dette selectedDette = detteTable.getSelectionModel().getSelectedItem();
        if (selectedDette != null) {
            // Ouvrir une nouvelle fenêtre ou afficher les détails
            showDetailsPage(selectedDette);
        }
    }

    private void showDetailsPage(Dette selectedDette) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fxml/DetteDetails.fxml"));
            Parent root = loader.load();

            DetteDetailsController detailsController = loader.getController();
            detailsController.setDette(selectedDette);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la vue des détails.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
