package repository.db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import core.config.Repository;
import entities.Article;

public class ArticleRepositoryDB implements Repository<Article> {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ges_dette";
    private static final String USER = "root";
    private static final String PASSWORD = null;

    @Override
    public void insert(Article objet) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "INSERT INTO `Article` (`libelle`, `prix`, `reference`, `quantite`) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, objet.getLibelle());
                pstmt.setDouble(2, objet.getPrix());
                pstmt.setString(3, objet.getReference());
                pstmt.setInt(4, objet.getQuantityInStock());
                pstmt.executeUpdate();
                System.out.println("Article inséré avec succès.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à votre BD: " + e.getMessage());
        }
    }

    @Override
    public void update(Article objet) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "UPDATE `Article` SET `libelle` = ?, `prix` = ?, `reference` = ?, `quantite` = ? WHERE `id` = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, objet.getLibelle());
                pstmt.setDouble(2, objet.getPrix());
                pstmt.setString(3, objet.getReference());
                pstmt.setInt(4, objet.getQuantityInStock());
                pstmt.setInt(5, objet.getId());
                pstmt.executeUpdate();
                System.out.println("Article mis à jour avec succès.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à votre BD: " + e.getMessage());
        }
    }

    @Override
    public List<Article> selectAll() {
        List<Article> articles = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM `Article`")) {
            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setLibelle(rs.getString("libelle"));
                article.setPrix(rs.getDouble("prix"));
                article.setReference(rs.getString("reference"));
                article.setQuantityInStock(rs.getInt("quantite"));
                articles.add(article);
            }
            System.out.println("Articles récupérés avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à votre BD: " + e.getMessage());
        }
        return articles;
    }

    @Override
    public Article selectById(int id) {
        Article article = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM `Article` WHERE `id` = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    article = new Article();
                    article.setId(rs.getInt("id"));
                    article.setLibelle(rs.getString("libelle"));
                    article.setPrix(rs.getDouble("prix"));
                    article.setReference(rs.getString("reference"));
                    article.setQuantityInStock(rs.getInt("quantite"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à votre BD: " + e.getMessage());
        }
        return article;
    }

   
    @Override
    public Article findby(int id) {
        return selectById(id);
    }

    @Override
    public void remove(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "DELETE FROM `Article` WHERE `id` = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1,id);
                pstmt.executeUpdate();
                System.out.println("Article supprimé avec succès.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à votre BD: " + e.getMessage());
        }
    }
}
