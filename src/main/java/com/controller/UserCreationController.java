package com.controller;
import java.io.IOException;
import java.util.List;

import com.core.factory.Factory;
import com.entities.Client;
import com.entities.Role;
import com.entities.User;
import com.services.UserServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UserCreationController {

    @FXML
    private TextField prenomField;
    
    @FXML
    private TextField nomField;
    
    @FXML
    private TextField loginField;
    
    @FXML
    private Button saveUserButton;
    
    @FXML
    private Button returnbtn;
    @FXML
    private ComboBox<String> clientComboBox;

    private UserServiceImpl userService;

    public void initialize() {
        userService = new UserServiceImpl(Factory.getInstanceUserRepository());
        loadClients();
    }

        private void loadClients() {
        List<Client> clientList = Factory.getInstanceClientRepository().selectAll();
        ObservableList<String> clientNames = FXCollections.observableArrayList();

        for (Client client : clientList) {
            clientNames.add(client.getSurname());
        }

        clientComboBox.setItems(clientNames);
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
        Role role = new Role();
        role.setNom("Client");
        newUser.setRole(role);
        newUser.setEtat(true); // Utilisateur actif par défaut

        // Sauvegarder le nouvel utilisateur
        userService.save(newUser);

        // Réinitialiser le formulaire
        prenomField.clear();
        nomField.clear();
        loginField.clear();

        // Fermer la fenêtre de création et revenir à la liste des utilisateurs
        closeWindow();
    }

    @FXML
    private void returnbtn() {
        try {
            // Charger le fichier FXML pour la liste des utilisateurs
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/Users.fxml"));
            AnchorPane userListRoot = loader.load();
            Scene scene = new Scene(userListRoot);
    
            // Obtenir le stage actuel de la fenêtre
            Stage stage = (Stage) returnbtn.getScene().getWindow(); // Utiliser returnbtn pour obtenir la scène
            stage.setScene(scene); // Définir la nouvelle scène
            stage.show(); // Afficher la scène mise à jour
    
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur de chargement du FXML
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) saveUserButton.getScene().getWindow();
        stage.close(); // Fermer la fenêtre de création d'utilisateur
    }
}
