package com.controller;

import com.core.factory.Factory;
import com.entities.User;
import com.services.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ConnexionController {

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private Button Seconnecter;

    private UserServiceImpl userService;

    @FXML
    public void initialize() {
        userService = new UserServiceImpl(Factory.getInstanceUserRepository());
    }

    // Cette méthode est appelée lors du clic sur le bouton "Se connecter"
    @FXML
    private void handleConnexion() {
        String loginValue = login.getText().trim();
        String passwordValue = password.getText().trim();
    
        System.out.println("Login: " + loginValue);  // Debugging
        System.out.println("Password: " + passwordValue);  // Debugging
    
        if (loginValue.isEmpty() || passwordValue.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.", Alert.AlertType.ERROR);
            return;
        }
    
        User user = userService.findByLoginAndPassword(loginValue, passwordValue);
        if (user == null) {
            showAlert("Erreur", "Identifiants incorrects. Veuillez réessayer.", Alert.AlertType.ERROR);
        } else {
            // Connexion réussie : vérifier le rôle de l'utilisateur
            String role = user.getRole().getNom();
            System.out.println("Role: " + role);  // Debugging
    
            // Ajout de la logique de contrôle d'accès en fonction des rôles
            if ("admin".equalsIgnoreCase(role.trim()) || "boutiquier".equalsIgnoreCase(role.trim())) {
                // Accès pour les administrateurs et boutiquiers
                loadPage("Dettes.fxml");
            } else if ("client".equalsIgnoreCase(role.trim())) {
                // Accès pour les clients
                loadPage("MesDettesClient.fxml");
            } else {
                // Si le rôle est inconnu ou incorrect
                showAlert("Erreur", "Rôle inconnu ou non autorisé : " + role, Alert.AlertType.ERROR);
            }
        }
    }
    
    

    // Méthode pour charger une nouvelle vue
    private void loadPage(String fxmlFile) {
        // Use the 'user' object that was passed during login instead of creating a new one
        User user = userService.findByLoginAndPassword(login.getText().trim(), password.getText().trim());
        
        if (user != null && user.getRole() != null) {
            // Récupération et nettoyage du rôle de l'utilisateur
            String userRole = user.getRole().getNom().trim();  // Enlever les espaces avant et après le rôle
        
            // Si l'utilisateur n'a pas le rôle requis, refuser l'accès à la page
            if (("Dettes.fxml".equals(fxmlFile) && !isRoleAllowed(userRole, "admin", "boutiquier")) || 
                ("MesDettesClient.fxml".equals(fxmlFile) && !isRoleAllowed(userRole, "client"))) {
                showAlert("Erreur", "Accès refusé à cette page.", Alert.AlertType.ERROR);
                return;
            }
        } else {
            showAlert("Erreur", "Le rôle de l'utilisateur est inconnu.", Alert.AlertType.ERROR);
            return;
        }
        
    
        try {
            // Utilisation de getClass().getResource() avec le bon chemin relatif
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/" + fxmlFile));
            
            // Vérification que la ressource FXML a bien été trouvée
            if (loader.getLocation() == null) {
                throw new IllegalStateException("La ressource FXML n'a pas été trouvée : " + fxmlFile);
            }
    
            Parent root = loader.load();
            Stage stage = (Stage) Seconnecter.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la vue demandée.", Alert.AlertType.ERROR);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            showAlert("Erreur", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private boolean isRoleAllowed(String role, String... allowedRoles) {
        if (role == null) return false;
        
        role = role.trim(); 
        
        for (String allowedRole : allowedRoles) {
            if (role.equalsIgnoreCase(allowedRole.trim())) {  // Comparer sans tenir compte de la casse
                return true;
            }
        }
        return false;
    }
      
    

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
