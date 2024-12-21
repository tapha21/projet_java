package com.controller;

import java.io.IOException;

import com.entities.Dette;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.Parent;
public class DetteDetailsController {
private Dette dette;
    @FXML
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
public void setDette(Dette dette) {
    // Perform necessary actions to set the dette
    this.dette = dette;
}

private void showAlert(String title, String message, Alert.AlertType alertType) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
}