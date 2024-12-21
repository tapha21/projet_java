package com.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.entities.Article;
import com.entities.Client;
import com.services.ClientServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;
public class CommandeListeController  implements Initializable{

    @FXML
    private TextField nomclient;

    @FXML
    private TextField numero;

    @FXML
    private TextField adresse;

    @FXML
    private ComboBox<String> article;

    @FXML
    private TextField quantite;

    @FXML
    private TextField prix;

    @FXML
    private Button ajouter;

    @FXML
    private TableView<Article> articletable;

    @FXML
    private TableColumn<Article, String> articlenom;

    @FXML
    private TableColumn<Article, Double> articleprix;

    @FXML
    private TableColumn<Article, Integer> articlequantite;

    @FXML
    private TableColumn<Article, Double> montant;

    @FXML
    private TextField montanttotal;
    @FXML
    private Button validerCommande;



    private ObservableList<Article> articles = FXCollections.observableArrayList();
    private final ClientServiceImpl clientService = new ClientServiceImpl();
    @Override

    public void initialize(URL location, ResourceBundle resources) {
        articlenom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        articleprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        articlequantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        montant.setCellValueFactory(new PropertyValueFactory<>("montant"));

        articletable.setItems(articles);
        numero.textProperty().addListener((observable, oldValue, newValue) -> rechercherClient(newValue));
        ajouter.setOnAction(event -> ajouterArticle());

        articletable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { 
                Article selectedArticle = articletable.getSelectionModel().getSelectedItem();
                if (selectedArticle != null) {
                    afficherDetailsArticle(selectedArticle);
                }
            }
            validerCommande.setOnAction(validerEvent -> validerCommande());
        });

    }
         private void rechercherClient(String numeroTelephone) {
        Client client = clientService.findCL(numeroTelephone);

        if (client != null) {
            nomclient.setText(client.getNom() + " " + client.getPrenom());
            adresse.setText(client.getVille() + ", " + client.getQuartier() + ", " + client.getNumerovilla());
            activerCommande(true);
        } else {
            nomclient.clear();
            adresse.clear();
            activerCommande(false);
        }
    }
    private void activerCommande(boolean active) {
        article.setDisable(!active);
        quantite.setDisable(!active);
        prix.setDisable(!active);
        ajouter.setDisable(!active);
        validerCommande.setDisable(!active);
        articletable.setDisable(!active);
    }

    private void ajouterArticle() {
        try {
            String selectedArticle = article.getSelectionModel().getSelectedItem();
            int quantity = Integer.parseInt(quantite.getText());
            double price = Double.parseDouble(prix.getText());

            Article existingArticle = articles.stream()
                    .filter(a -> a.getLibelle().equals(selectedArticle))
                    .findFirst()
                    .orElse(null);

            if (existingArticle != null) {
                existingArticle.setQuantityInStock(existingArticle.getQuantityInStock() + quantity);
                existingArticle.setPrix(price); 
                existingArticle.setMontant(existingArticle.getPrix() * existingArticle.getQuantityInStock());
            } else {
                Article newArticle = new Article(selectedArticle, price, quantity);
                articles.add(newArticle);
            }

            mettreAJourMontantTotal();
        } catch (NumberFormatException e) {
            afficherMessageErreur("Veuillez entrer une quantité et un prix valides.");
        }
    }
    private void afficherDetailsArticle(Article article) {
        article.setQuantityInStock(Integer.parseInt(quantite.getText()));
        article.setPrix(Double.parseDouble(prix.getText()));
        article.setMontant(article.getQuantityInStock() * article.getPrix());
        articletable.refresh();
        mettreAJourMontantTotal();
    }

 private void mettreAJourMontantTotal() {
        double total = articles.stream().mapToDouble(Article::getMontant).sum();
        montanttotal.setText(String.valueOf(total));
    }

    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void validerCommande() {
        System.out.println("Commande validée avec succès !");
    }


       
}

