    package com.controller;
    import javafx.scene.Node;
    import javafx.scene.Parent;
    import com.core.RepoInter.ArticleRepository;
    import com.core.RepoInter.DetailsRepository;
    import com.entities.Article;
    import com.services.ArticleServiceImpl;
    import com.core.factory.Factory;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Scene;
    import javafx.scene.control.Button;
    import javafx.scene.control.ComboBox;
    import javafx.scene.control.TableColumn;
    import javafx.scene.control.TableView;
    import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.scene.layout.AnchorPane;
    import javafx.stage.Stage;


    import java.io.IOException;

    public class ArticleListController {

        @FXML
        private Button creerButton;

        @FXML
        private TableView<Article> articleTable;

        @FXML
        private TableColumn<Article, Integer> IDColumn;

        @FXML
        private TableColumn<Article, String> refColumn;

        @FXML
        private TableColumn<Article, String> libelleColumn;

        @FXML
        private TableColumn<Article, Integer> qteStockColumn;

        @FXML
        private TableColumn<Article, Double> prixColumn;

        @FXML
        private ComboBox<String> filterComboBox;
        @FXML
        private Button articlesButton;
        
        @FXML
        private Button dettesButton;
        
        @FXML
        private Button clientsButton;
        
        @FXML
        private Button usersButton;

        private final ArticleRepository articleRepository = Factory.getInstanceArticleRepository();
        private final DetailsRepository detailsRepository = Factory.getInstanceDetails();
        private final ArticleServiceImpl articleService = new ArticleServiceImpl(articleRepository, detailsRepository);

        @FXML
        public void initialize() {
            setupTable();
            listArticles();
            filterComboBox.getItems().addAll("Tous", "Disponible", "Indisponible");
            filterComboBox.getSelectionModel().select("Tous");
            filterComboBox.setOnAction(event -> filterArticles());
            articlesButton.setOnAction(event -> loadPage("Articles.fxml"));
            dettesButton.setOnAction(event -> loadPage("Dettes.fxml"));
            clientsButton.setOnAction(event -> loadPage("Client.fxml"));
            usersButton.setOnAction(event -> loadPage("Users.fxml"));
        }

        private void setupTable() {
            IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            refColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
            libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            qteStockColumn.setCellValueFactory(new PropertyValueFactory<>("quantityInStock"));
            prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        }

        private void listArticles() {
            ObservableList<Article> articles = FXCollections.observableArrayList(articleService.find());
            articleTable.setItems(articles);
        }

        private void filterArticles() {
            String selectedFilter = filterComboBox.getSelectionModel().getSelectedItem();
            ObservableList<Article> allArticles = FXCollections.observableArrayList(articleService.find());
            ObservableList<Article> filteredArticles = FXCollections.observableArrayList();
        
            switch (selectedFilter) {
                case "Disponible":
                    for (Article article : allArticles) {
                        if (article.getQuantityInStock() > 0) {
                            filteredArticles.add(article);
                        }
                    }
                    articleTable.setItems(filteredArticles);
                    break;
                case "Indisponible":
                    for (Article article : allArticles) {
                        if (article.getQuantityInStock() == 0) {
                            filteredArticles.add(article);
                        }
                    }
                    articleTable.setItems(filteredArticles);
                    break;
                default:
                    articleTable.setItems(allArticles);
                    break;
            }
        }
        

        @FXML
        public void creerButton() {
            openForm();
        }
        private void openForm() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ArticleForm.fxml"));
                AnchorPane root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Créer un article");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Erreur lors du chargement de la vue : " + fxmlPath);
            }
        }

    }
