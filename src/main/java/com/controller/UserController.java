package com.controller;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.entities.Role;
import com.entities.User;
import com.services.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import com.core.factory.Factory;

import javafx.fxml.FXMLLoader;

public class UserController implements Initializable {
    @FXML
    private Button articlesButton;

    @FXML
    private Button dettesButton;

    @FXML
    private Button clientsButton;

    @FXML
    private Button usersButton;

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
    
    @FXML
    private TableColumn<User, Boolean> etat;
    
    @FXML
    private TextField filtretext;
    
    @FXML
    private Button filtrer;
    
    @FXML
    private Button user;
    
    @FXML
    private AnchorPane creationForm;
    
    @FXML
    private TextField prenomField;
    
    @FXML
    private TextField nomField;
    
    @FXML
    private TextField loginField;
    
    @FXML
    private Button saveUser;

    @FXML
    private Button createUserButton;

    
    private UserServiceImpl userService;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
        userService = new UserServiceImpl(Factory.getInstanceUserRepository());

        // Configurer les colonnes pour mapper les propriétés de l'objet User
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        dettesButton.setOnAction(event -> loadPage("Dettes.fxml"));
        articlesButton.setOnAction(event -> loadPage("Articles.fxml"));
        clientsButton.setOnAction(event -> loadPage("Client.fxml"));
        usersButton.setOnAction(event -> loadPage("Users.fxml"));
        createUserButton.setOnAction(event -> loadPage("Userform.fxml"));

        // Charger les utilisateurs dans la table
        loadUsers();

        // Ajouter un écouteur pour le bouton de filtre
        filtrer.setOnAction(event -> filtrerUsers());
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    private void loadUsers() {
        List<User> userList = userService.findAll(); // Récupérer la liste des utilisateurs
        ObservableList<User> observableUserList = FXCollections.observableArrayList(userList);
        userTable.setItems(observableUserList); // Ajouter les utilisateurs dans la table
    }

    private void filtrerUsers() {
        String filtre = filtretext.getText().trim().toLowerCase(); // Récupérer le texte du filtre
        List<User> filteredUsers = userService.findAll().stream()
                .filter(user -> user.getPrenom().toLowerCase().contains(filtre) ||
                                user.getNom().toLowerCase().contains(filtre) ||
                                user.getLogin().toLowerCase().contains(filtre))
                .toList();
        userTable.setItems(FXCollections.observableArrayList(filteredUsers)); // Mettre à jour la table
    }

    @FXML
    private void CreateUser() {
        userTable.setVisible(false);
        creationForm.setVisible(true);
    }

    @FXML
    private void saveUser() {
        String prenom = prenomField.getText();
        String nom = nomField.getText();
        String login = loginField.getText();

        // Créer un nouvel utilisateur
        User newUser = new User();
        newUser.setPrenom(prenom);
        newUser.setNom(nom);
        newUser.setLogin(login);
        Role role= new Role();
        role.setNom("CLIENT");
        newUser.setRole(role); 
        newUser.setEtat(true);

        userService.save(newUser);

        // Réinitialiser le formulaire
        prenomField.clear();
        nomField.clear();
        loginField.clear();

        // Recharger la table avec les nouveaux utilisateurs
        loadUsers();

        // Revenir à l'affichage de la table
        creationForm.setVisible(false);
        userTable.setVisible(true);
    }

     private void loadPage(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/" + fxmlFile));
            Scene scene = new Scene(root);
            Stage stage = (Stage) usersButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gestion des erreurs à améliorer
        }
    }
}
