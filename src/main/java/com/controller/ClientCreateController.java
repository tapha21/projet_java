package com.controller;

import java.io.IOException;

import com.core.factory.Factory;
import com.entities.Client;
import com.services.ClientServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientCreateController {
    @FXML
    private TextField surnameField;

    @FXML
    private TextField telephoneField;

    @FXML
    private TextField adresseField;

    @FXML
    private Button creerButton;
    @FXML
    private Button returnbtn;
    private final ClientServiceImpl clientService;

    public ClientCreateController() {
        this.clientService = new ClientServiceImpl(Factory.getInstanceClientRepository());
    }

    @FXML
    public void initialize() {
        if (creerButton != null) {
            creerButton.setOnAction(event -> createClient());
        } else {
            System.err.println("Button is null in initialize method.");
        }    }
    @FXML
    public void createClient() {
        String surname = surnameField.getText().trim();
        String telephone = telephoneField.getText().trim();
        String adresse = adresseField.getText().trim();

        if (!surname.isEmpty() && !telephone.isEmpty() && !adresse.isEmpty()) {
            Client newClient = new Client();
            newClient.setSurname(surname);
            newClient.setTelephone(telephone);
            newClient.setAdresse(adresse);

            clientService.save(newClient);

            // Afficher un message ou rediriger vers la liste des clients
            System.out.println("Client créé avec succès !");
        } else {
            // Afficher un message d'erreur ou une alerte
            System.out.println("Veuillez remplir tous les champs !");
        }
    }

     @FXML
    private void returnbtn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/Client.fxml"));
            AnchorPane clientListRoot = loader.load();
            Scene scene = new Scene(clientListRoot);
            Stage stage = (Stage) clientListRoot.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur de chargement du FXML
        }
    }
}
