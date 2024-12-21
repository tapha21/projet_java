package repository.postgreImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import DataSource.DataBasePostgreImpl;
import core.RepoInter.DetteRepository;
import entities.Article;
import entities.Client;
import entities.Dette;
import entities.Paiement;

public class DetteRepositoryPostgreImpl extends DataBasePostgreImpl<Dette> implements DetteRepository {
    private ClientRepositoryPostgreImpl clientRepositoryPostgreImpl;
    protected String tableName;

    private static final String INSERT_QUERY_TEMPLATE = "INSERT INTO dette(\n" + //
                "\tmontant, \"montantverser\", reglee, client_id, date)\n" + //
                "\tVALUES ( ?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY_TEMPLATE = "UPDATE dette SET date = ?, montant = ?, \"montantverser\" = ?, reglee = ?, client_id = ? WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM dette";
    private static final String SELECT_BY_ID_QUERY_TEMPLATE = "SELECT d.* FROM dette d \"\n" + //
                "+ \"JOIN client c ON d.client_id = c.id \"\n" + //
                "+ \"WHERE c.telephone = ? AND d.reglee = true;";
    private static final String details = "INSERT INTO details(\n" + //
                "\"prixVente\", \"qteVente\", \"prixTotal\", article_id, dette_id)\n" + //
                "\tVALUES (?, ?, ?, ?, ?);";

    private static final String SELECT_BY_CLIENT_ID_QUERY_TEMPLATE = "SELECT * FROM dette WHERE client_id = ?";
    private static final String DELETE_QUERY_TEMPLATE = "DELETE FROM dette WHERE id = ?";
    private static final String SelectbyID = "SELECT * FROM dette WHERE id = ?";



    @Override
    public void insert(Dette objet) {
        String insertQuery = String.format(INSERT_QUERY_TEMPLATE, tableName);
        String detailsInsertQuery = String.format(details, tableName);
        try {
            this.connect();
            this.init(insertQuery);
            setInsertParameters(this.ps, objet);
            this.executeUpdate();
            ResultSet rs = this.ps.getGeneratedKeys();
            if (rs.next()) {
                objet.setId(rs.getInt(1));
            }
            if (objet.getArticles() != null && !objet.getArticles().isEmpty()) {
                for (Article article : objet.getArticles()) {
                    this.init(detailsInsertQuery);
                    setDetailInsertParameters(this.ps, article, objet.getId()); 
                    this.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion de la dette : " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    @Override
    public void update(Dette objet) {
        String updateQuery = String.format(UPDATE_QUERY_TEMPLATE, tableName);
        try {
            this.connect();
            this.init(updateQuery);
            setUpdateParameters(this.ps, objet);
            this.ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la dette : " + e.getMessage());
        } finally {
            closeResources();
        }
    }
    @Override
    public List<Dette> selectAll() {
        List<Dette> dettes = new ArrayList<>();
        String selectQuery = String.format(SELECT_ALL_QUERY, tableName);
        ResultSet resultSet = null;
        try {
            connect();
            this.init(selectQuery);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                dettes.add(convertToObject(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
        } finally {
            closeResources(resultSet);
        }
        return dettes;
    }

    @Override
    public Dette selectById(int id) {
        String selectQuery = String.format(SELECT_BY_ID_QUERY_TEMPLATE, tableName);
        Dette dette = null;
        try {
            this.connect();
            this.init(selectQuery);
            this.ps.setInt(1, id);
            ResultSet resultSet = this.ps.executeQuery();
            if (resultSet.next()) {
                dette = convertToObject(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection de la dette par ID : " + e.getMessage());
        } finally {
            closeResources();
        }
        return dette;
    }

    @Override
    public Dette findby(int id) {
        String selectQuery = String.format(SelectbyID, tableName);
        Dette dette = null;
        try {
            this.connect();
            this.init(selectQuery);
            this.ps.setInt(1, id);
            ResultSet resultSet = this.ps.executeQuery();
            if (resultSet.next()) {
                dette = convertToObject(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection de la dette par ID : " + e.getMessage());
        } finally {
            closeResources();
        }
        return dette;    }

    @Override
    public void remove(int id) {
        String deleteQuery = String.format(DELETE_QUERY_TEMPLATE, tableName);
        try {
            this.connect();
            this.init(deleteQuery);
            this.ps.setInt(1, id);
            this.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la dette : " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    @Override
    public List<Dette> findByClient(Client client) {
        String selectQuery = String.format(SELECT_BY_CLIENT_ID_QUERY_TEMPLATE, tableName);
        List<Dette> dettes = new ArrayList<>();
        try {
            this.connect();
            this.init(selectQuery);
            this.ps.setInt(1, client.getId());
            ResultSet resultSet = this.ps.executeQuery();
            while (resultSet.next()) {
                dettes.add(convertToObject(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection des dettes par client : " + e.getMessage());
        } finally {
            closeResources();
        }
        return dettes;
    }

    public List<Dette> findByEtat(boolean etat) {
        String selectQuery = "SELECT * FROM dette WHERE reglee = ?";
        List<Dette> dettes = new ArrayList<>();
        try {
            this.connect();
            this.init(selectQuery);
            this.ps.setBoolean(1, etat);  
            ResultSet resultSet = this.ps.executeQuery();
            while (resultSet.next()) {
                dettes.add(convertToObject(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection des dettes par état : " + e.getMessage());
        } finally {
            closeResources();
        }
        return dettes;
    }
    



    public List<Dette> selectAllByCondition(String condition) {
        List<Dette> dettes = new ArrayList<>();
        String selectQuery = String.format("SELECT * FROM %s WHERE %s", tableName, condition);
        ResultSet resultSet = null;
        try {
            this.connect();
            this.init(selectQuery);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                dettes.add(convertToObject(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection des dettes par condition : " + e.getMessage());
        } finally {
            closeResources(resultSet);
        }
        return dettes;
    }

    protected void setInsertParameters(PreparedStatement pstmt, Dette objet) throws SQLException {
        pstmt.setDouble(1, objet.getMontant());
        pstmt.setDouble(2, objet.getMontantVerser());
        pstmt.setBoolean(3, objet.isReglee());
        pstmt.setInt(4, objet.getClient().getId());
        if (objet.getDate() != null) {
            pstmt.setTimestamp(5, Timestamp.valueOf(objet.getDate()));
        } else {
            pstmt.setNull(5, java.sql.Types.TIMESTAMP);
        }
    }


    protected void setUpdateParameters(PreparedStatement pstmt, Dette objet) throws SQLException {
        pstmt.setTimestamp(1, Timestamp.valueOf(objet.getDate()));
        pstmt.setDouble(2, objet.getMontant());
        pstmt.setDouble(3, objet.getMontantVerser());
        pstmt.setBoolean(4, objet.isReglee());
        pstmt.setInt(5, objet.getClient().getId());
        pstmt.setInt(6, objet.getId());
    }

    @Override
    public Dette convertToObject(ResultSet rs) throws SQLException {
        Dette dette = new Dette();
        dette.setId(rs.getInt("id"));
        dette.setMontant(rs.getDouble("montant"));
        dette.setMontantVerser(rs.getDouble("montantverser"));
        dette.setReglee(rs.getBoolean("reglee"));
        if (rs.getTimestamp("date") != null) {
            dette.setDate(rs.getTimestamp("date").toLocalDateTime());
        }
        Client client = new Client();
        client.setId(rs.getInt("client_id"));
        dette.setClient(client);
        return dette;
    }

    // Méthode utilitaire pour fermer les ressources
    private void closeResources() {
        closeResources(null);
    }

    // Méthode utilitaire pour fermer les ressources avec ResultSet
    private void closeResources(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (ps != null) {
                ps.close();
            }
            closeConnection();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
        }
    }

    @Override
    public String generateSQL() {
        // Exemple de génération d'une requête SQL dynamique
        StringBuilder query = new StringBuilder("SELECT * FROM ");
        query.append(tableName).append(" WHERE 1=1");
        // Ajouter d'autres conditions selon les besoins
        return query.toString();
    }

    @Override
    public void setField(Dette entity) {
        // Implémenter si nécessaire
        throw new UnsupportedOperationException("Unimplemented method 'setField'");
    }

    @Override
    public void selectByTel(String tel) {
        String query = String.format("SELECT d.* FROM %s d JOIN clients c ON d.client_id = c.id WHERE c.telephone = ?", tableName);
        try {
            this.connect();
            this.init(query);
            this.ps.setString(1, tel);
            ResultSet resultSet = this.ps.executeQuery();
            while (resultSet.next()) {
                // Traiter les résultats
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection des dettes par téléphone : " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    @Override
    public List<Dette> NonSolde(Client client) {
       String query = String.format("SELECT * FROM dette WHERE client_id = ? AND reglee = false", tableName);
    List<Dette> dettes = new ArrayList<>();
    
    try {
        this.connect();
        this.init(query);
        this.ps.setInt(1, client.getId());
        ResultSet resultSet = this.ps.executeQuery();
        
        while (resultSet.next()) {
            Dette dette = convertToObject(resultSet);
            List<Article> articles = recupererArticlesParDette(dette.getId());
            dette.setArticles(articles);
            List<Paiement> paiements = recupererPaiementsParDette(dette.getId());
            dette.setPaiements(paiements);
            dettes.add(dette);
        }
    } catch (SQLException e) {
        System.out.println("Erreur lors de la sélection des dettes non soldées : " + e.getMessage());
    } finally {
        closeResources();
    }
    
    return dettes;
    }

    @Override
    public void save(Dette dette) {
        String insertQuery = String.format(INSERT_QUERY_TEMPLATE, tableName);
        try {
            this.connect();
            this.init(insertQuery);
            setInsertParameters(this.ps, dette);
            this.executeUpdate();
            ResultSet rs = this.ps.getGeneratedKeys();
            if (rs.next()) {
                dette.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion de la dette : " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    @Override
    public Dette findById(int id) {
        String selectQuery = String.format(SelectbyID, tableName);
        Dette dette = null;
        try {
            this.connect();
            this.init(selectQuery);
            this.ps.setInt(1, id);
            ResultSet resultSet = this.ps.executeQuery();
            if (resultSet.next()) {
                dette = convertToObject(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection de la dette par ID : " + e.getMessage());
        } finally {
            closeResources();
        }
        return dette;
    }
    private List<Article> recupererArticlesParDette(int detteId) {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM details WHERE dette_id = ?";
        try {
            this.connect();
            this.init(query);
            this.ps.setInt(1, detteId);
            ResultSet resultSet = this.ps.executeQuery();
            
            while (resultSet.next()) {
                Article article = convertToObjectArt(resultSet);
                articles.add(article);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des articles : " + e.getMessage());
        } finally {
            closeResources();
        }
    
        return articles;
    }
    
    private List<Paiement> recupererPaiementsParDette(int detteId) {
        List<Paiement> paiements = new ArrayList<>();
        String query = "SELECT * FROM paiement WHERE id_dette = ?";
    
        try {
            this.connect();
            this.init(query);
            this.ps.setInt(1, detteId);
            ResultSet resultSet = this.ps.executeQuery();
    
            while (resultSet.next()) {
                Paiement paiement = new Paiement();
                paiement.setMontant(resultSet.getDouble("montant"));
                paiement.setDate(resultSet.getTimestamp("date").toLocalDateTime());        
                paiements.add(paiement);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des paiements : " + e.getMessage());
        } finally {
            closeResources();
        }
    
        return paiements;
    }
    public Article convertToObjectArt(ResultSet rs) throws SQLException {
        Article article = new Article();
        article.setId(rs.getInt("article_id"));
        article.setLibelle(rs.getString("article_libelle"));
        article.setPrix(rs.getDouble("article_prix"));
        article.setReference(rs.getString("article_reference"));
        article.setQuantityInStock(rs.getInt("article_quantite"));
        return article;
    }
    protected void setDetailInsertParameters(PreparedStatement pstmt, Article article, int detteId) throws SQLException {
        pstmt.setInt(1, article.getId());
        pstmt.setDouble(2, article.getPrix());
        pstmt.setInt(3, article.getQuantityInStock());
        pstmt.setDouble(4, article.getPrixTotal());
        pstmt.setInt(5, article.getId()); 
        pstmt.setInt(6, detteId);
    }
    
}
