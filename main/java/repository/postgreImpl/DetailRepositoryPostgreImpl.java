package repository.postgreImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataSource.DataBasePostgreImpl;
import core.RepoInter.DetailsRepository;
import entities.Article;
import entities.Detail;
import entities.Dette;
import entities.Paiement;

public class DetailRepositoryPostgreImpl extends DataBasePostgreImpl<Detail> implements DetailsRepository {

    @Override
    public void insert(Detail objet) {
        String insertQuery = "INSERT INTO detail (quantite, prix_unitaire, article_id, dette_id) VALUES (?, ?, ?, ?)";
        try {
            this.connect();
            this.init(insertQuery);
            setInsertParameters(this.ps, objet);
            this.ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion du détail : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Detail objet) {
        String updateQuery = "UPDATE detail SET quantite = ?, prix_unitaire = ?, article_id = ?, dette_id = ? WHERE id = ?";
        try {
            this.connect();
            this.init(updateQuery);
            setInsertParameters(this.ps, objet);
            this.ps.setInt(5, objet.getId());
            this.ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du détail : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Detail> selectAll() {
        List<Detail> details = new ArrayList<>();
        String selectQuery = "SELECT * FROM detail";
        try {
            this.connect();
            this.init(selectQuery);
            ResultSet resultSet = this.ps.executeQuery();
            while (resultSet.next()) {
                details.add(convertToObject(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des détails : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return details;
    }

    @Override
    public Detail selectById(int id) {
        String selectQuery = "SELECT * FROM detail WHERE id = ?";
        Detail detail = null;
        try {
            this.connect();
            this.init(selectQuery);
            this.ps.setInt(1, id);
            ResultSet resultSet = this.ps.executeQuery();
            if (resultSet.next()) {
                detail = convertToObject(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du détail par ID : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return detail;
    }

    @Override
    public void remove(int id) {
        String deleteQuery = "DELETE FROM detail WHERE id = ?";
        try {
            this.connect();
            this.init(deleteQuery);
            this.ps.setInt(1, id);
            this.ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du détail : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Detail findby(int id) {
        return selectById(id);
    }

    @Override
    public String generateSQL() {
        return "SELECT * FROM detail";
    }

    @Override
    public void setField(Detail entity) {
        // entity.setQuantite(10);
        // entity.setPrixUnitaire(100.0);
    }

    @Override
    public List<Article> findByDette(Dette dette) {
        String selectQuery = "SELECT a.* FROM details d JOIN article a ON d.article_id = a.id WHERE d.dette_id = ?";
        List<Article> articles = new ArrayList<>();
        try {
            this.connect();
            this.init(selectQuery);
            this.ps.setInt(1, dette.getId());
            ResultSet resultSet = this.ps.executeQuery();
            while (resultSet.next()) {
                articles.add(convertToArticle(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection des articles par dette : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return articles;
    }

    private Article convertToArticle(ResultSet rs) throws SQLException {
        Article article = new Article();
        article.setId(rs.getInt("id"));
        article.setLibelle(rs.getString("libelle"));
        article.setPrix(rs.getDouble("prix"));
        article.setReference(rs.getString("reference"));
        return article;
    }

    @Override
    protected void setInsertParameters(PreparedStatement pstmt, Detail objet) throws SQLException {
        pstmt.setInt(1, objet.getQuantite());
        pstmt.setDouble(2, objet.getPrixVente());
        pstmt.setInt(3, objet.getArticle().getId());
        pstmt.setInt(4, objet.getDette().getId());
    }

    @Override
    public Detail convertToObject(ResultSet rs) throws SQLException {
        Detail detail = new Detail();
        detail.setId(rs.getInt("id"));
        detail.setQuantite(rs.getInt("quantite"));
        detail.setPrixTotal(rs.getInt("prix"));
        detail.setQteVente(rs.getDouble("QteVente"));
        detail.setPrixVente(rs.getDouble("prixVente"));
        return detail;
    }

    @Override
    public List<Paiement> findDette(Dette dette) {
        String selectQuery = "SELECT * FROM paiement WHERE id_dette = ?";
        List<Paiement> paiements = new ArrayList<>();
        try {
            this.connect();
            this.init(selectQuery);
            this.ps.setInt(1, dette.getId());
            ResultSet resultSet = this.ps.executeQuery();
            while (resultSet.next()) {
                paiements.add(convertToPaiement(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des paiements de la dette : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return paiements;
    }

    private Paiement convertToPaiement(ResultSet rs) throws SQLException {
        Paiement paiement = new Paiement();
        paiement.setId(rs.getInt("id"));
        paiement.setMontant(rs.getDouble("montant"));
        paiement.setCreatAt(rs.getTimestamp("date_paiement").toLocalDateTime()); 
        paiement.setUpdateAt(rs.getTimestamp("date_paiement").toLocalDateTime());
        return paiement;
    }
}
