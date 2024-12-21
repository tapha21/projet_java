package com.controller;

import com.core.factory.Factory;
import com.entities.User;
import com.services.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField; 
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UserListController {

    @FXML
    private Button createUserButton;
    @FXML
    private Button articlesButton;
    @FXML
    private Button dettesButton;
    @FXML
    private Button clientsButton;
    @FXML
    private TableView<User> userTable;
    
    @FXML
    private TableColumn<User, Integer> IDColumn;
    @FXML
    private TableColumn<User, String> prenom;
    @FXML
    private TableColumn<User, String> nom;
    @FXML
    private TableColumn<User, String> login;
    @FXML
    private TableColumn<User, String> role;
    // @FXML
    // private TableColumn<User, String> password;
    @FXML
    private TextField filtretext; 

    private UserServiceImpl userService;

    public void initialize() {
        try {
            userService = new UserServiceImpl(Factory.getInstanceUserRepository());

            // Configurer les colonnes pour mapper les propriétés de l'objet User
            IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            login.setCellValueFactory(new PropertyValueFactory<>("login"));
            role.setCellValueFactory(new PropertyValueFactory<>("roleName"));
            // password.setCellValueFactory(new PropertyValueFactory<>("password"));
            // Action pour les boutons
            articlesButton.setOnAction(event -> loadPage("Articles.fxml"));
            dettesButton.setOnAction(event -> loadPage("Dettes.fxml"));
            clientsButton.setOnAction(event -> loadPage("Client.fxml"));

            // Charger les utilisateurs dans la table
            loadUsers();
        } catch (Exception e) {
            e.printStackTrace(); // Gestion des erreurs à améliorer
        }
    }

    private void loadUsers() {
        List<User> userList = userService.findAll(); // Récupérer la liste des utilisateurs
        ObservableList<User> observableUserList = FXCollections.observableArrayList(userList);
        userTable.setItems(observableUserList); // Ajouter les utilisateurs dans la table
    }
      @FXML
        public void handleCreateUserButton() {
            openForm();
        }
        private void openForm() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/UserForm.fxml"));
                AnchorPane root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Créer un Users");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    private void loadPage(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/" + fxmlFile));
            Scene scene = new Scene(root);
            Stage stage = (Stage) createUserButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gestion des erreurs à améliorer
        }
    }
@FXML
private void handleFilterAction() {
    String filterText = filtretext.getText().toLowerCase(); // Récupérer le texte du filtre et le convertir en minuscules
    List<User> filteredUsers = userService.findAll(); // Récupérer tous les utilisateurs

    // Filtrer les utilisateurs par prénom si le texte du filtre n'est pas vide
    if (!filterText.isEmpty()) {
        filteredUsers = filteredUsers.stream()
            .filter(user -> user.getPrenom() != null && user.getPrenom().toLowerCase().contains(filterText))
            .collect(Collectors.toList());
    }

    // Mettre à jour la TableView avec la liste filtrée
    ObservableList<User> observableUserList = FXCollections.observableArrayList(filteredUsers);
    userTable.setItems(observableUserList);
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

    // Méthode générique pour changer de scène
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
