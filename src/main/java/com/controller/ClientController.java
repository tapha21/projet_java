package com.controller;
import java.io.IOException;

import com.core.RepoInter.ArticleRepository;
import com.core.RepoInter.DetailsRepository;
import com.core.factory.Factory;
import com.entities.Client;
import com.services.ArticleServiceImpl;
import com.services.ClientServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class ClientController {
    @FXML
    private Button articlesButton;

    @FXML
    private Button dettesButton;

    @FXML
    private Button clientsButton;

    @FXML
    private Button usersButton;

    @FXML
    private TableView<Client> clientTable;

    @FXML
    private TableColumn<Client, Integer> IDColumn;

    @FXML
    private TableColumn<Client, String> Surname;

    @FXML
    private TableColumn<Client, String> telephone;

    @FXML
    private TableColumn<Client, String> adresse;

    @FXML
    private TableColumn<Client, String> actions;

    @FXML
    private TextField filtretext;

    @FXML
    private Button creer;

    @FXML
    private Button filtrer;

    private ClientServiceImpl clientService;

    private ComboBox<String> clientComboBox; 

    // Constructor
    public ClientController() {
        this.clientService = new ClientServiceImpl(Factory.getInstanceClientRepository());
    }

    @FXML
    public void initialize() {
        try {
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        Surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        dettesButton.setOnAction(event -> loadPage("Dettes.fxml"));
        articlesButton.setOnAction(event -> loadPage("Articles.fxml"));
        clientsButton.setOnAction(event -> loadPage("Client.fxml"));
        usersButton.setOnAction(event -> loadPage("Users.fxml"));
        loadClients();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void CreateClient() {
        // Implémenter la logique pour créer un nouveau client
    }

    @FXML
    private void filterClients() {
        String filterText = filtretext.getText().trim();
        List<Client> filteredClients;

        if (!filterText.isEmpty()) {
            filteredClients = clientService.findAll().stream()
                    .filter(client -> client.getTelephone().contains(filterText) || client.getSurname().contains(filterText))
                    .toList();
        } else {
            filteredClients = clientService.findAll();
        }

        clientTable.getItems().setAll(filteredClients);
    }

    private void loadClients() {
        List<Client> clients = clientService.findAll();
        clientTable.getItems().setAll(clients);
    }
     private void loadPage(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/" + fxmlFile));
            Scene scene = new Scene(root);
            Stage stage = (Stage) articlesButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
}
