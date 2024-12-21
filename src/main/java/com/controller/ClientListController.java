package com.controller;

import com.core.factory.Factory;
import com.entities.Client;
import com.services.ClientServiceImpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ClientListController {
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
    private TextField filtretext;

    @FXML
    private Button filtrer;
    @FXML
    private Button articlesButton;
    
    @FXML
    private Button dettesButton;
    
    @FXML
    private Button clientsButton;
    @FXML
    private Button usersButton;
    
    @FXML
    private Button creerButton;   
    
    @FXML
    private Button filtre; 

    private final ClientServiceImpl clientService;

    public ClientListController() {
        this.clientService = new ClientServiceImpl(Factory.getInstanceClientRepository());
    }

    @FXML
    public void initialize() {
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        Surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        loadClients();
        articlesButton.setOnAction(event -> loadPage("Articles.fxml"));
        dettesButton.setOnAction(event -> loadPage("Dettes.fxml"));
        clientsButton.setOnAction(event -> loadPage("Client.fxml"));
        usersButton.setOnAction(event -> loadPage("Users.fxml"));
        filtrer.setOnAction(event -> filterClients());
    }

    private void filterClients() {
    String filterText = filtretext.getText().trim();
    List<Client> filteredClients = clientService.findAll().stream()
    .filter(client -> client.getSurname().toLowerCase().contains(filterText.toLowerCase())) 
    .collect(Collectors.toList());

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

@FXML
public void CreateClient() {
    openForm();
} 
        private void openForm() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ClientForm.fxml"));
                AnchorPane root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Créer un Clients");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
@FXML
private void handleArticlesButtonAction(ActionEvent event) {
    switchScene(event, "/com/views/Articles.fxml");
}

@FXML
private void handleDettesButtonAction(ActionEvent event) {
    switchScene(event, "/com/views/Dettes.fxml");
}

@FXML
private void handleClientsButtonAction(ActionEvent event) {
    switchScene(event, "/com/views/Client.fxml");
}

@FXML
private void handleUsersButtonAction(ActionEvent event) {
    switchScene(event, "/com/views/Users.fxml");
}

private void switchScene(ActionEvent event, String fxmlPath) {
    try {
        // Charger la nouvelle scène
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);

        // Récupérer la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Appliquer la nouvelle scène
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        System.err.println("Erreur lors du chargement de la vue : " + fxmlPath);
    }
}
}
