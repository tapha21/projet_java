package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage; 
    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        scene = new Scene(loadFXML("Connexion"), 640, 480);
        stage.setTitle("Gestion de Dettes");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    public static void switchToView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
            Parent root = loader.load();
            Stage stage = (Stage) App.stage.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue : " + e.getMessage());
        }
    }

}