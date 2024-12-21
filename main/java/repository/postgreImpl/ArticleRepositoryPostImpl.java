package repository.postgreImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataSource.DataBasePostgreImpl;
import core.RepoInter.ArticleRepository;
import entities.Article;

public class ArticleRepositoryPostImpl extends DataBasePostgreImpl<Article> implements ArticleRepository {
    protected String tableName;
    private static final String INSERT_QUERY_TEMPLATE = "INSERT INTO article (libelle, prix, reference, quantityInStock) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY_TEMPLATE = "UPDATE article " + 
    "SET libelle = ?, prix = ?, reference = ?, quantityInStock = ? " + 
    "WHERE id = ?;";
    private static final String SELECT_BY_ID_QUERY_TEMPLATE = "SELECT a.* \n" + //
                "FROM article a\n" + //
                "JOIN details d ON a.id = d.article_id\n" + //
                "WHERE d.dette_id = ?";
    private static final String SELECT = "SELECT * FROM article WHERE id = ?";

    private static final String DELETE_QUERY_TEMPLATE = "DELETE FROM article WHERE id = ?";
    private static final String UPDATE_QUANTITY_QUERY = "UPDATE article SET quantite = ? WHERE id = ?";
    private static final String ACTIVE_ARTICLE_QUERY = "UPDATE article SET etat = true WHERE id = ?";
    private static final String DESACTIVE_ARTICLE_QUERY =  "UPDATE article SET etat = false WHERE id = ?";
    private static final String LISTE_DESACTIVE_QUERY =  "SELECT * FROM article WHERE etat = false";
    
    private final String SELECT_ALL_QUERY = 
        "SELECT id AS id, libelle AS libelle, prix AS prix, " +
        "reference AS reference, quantityInStock AS quantityInStock FROM article;"; 

    public ArticleRepositoryPostImpl() {
        this.tableName = "article";
    }

    @Override
    public void insert(Article objet) {
        String insertQuery = String.format(INSERT_QUERY_TEMPLATE, tableName);
        try {
            this.connect();
            this.init(insertQuery);
            setInsertParameters(this.ps, objet);
            this.executeUpdate();
            ResultSet rs = this.ps.getGeneratedKeys();
            if (rs.next()) {
                objet.setId(rs.getInt(1)); 
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion de l'article : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Article objet) {
        String updateQuery = String.format(UPDATE_QUERY_TEMPLATE, tableName);
        try {
            this.connect();
            this.init(updateQuery);
            this.setUpdateParameters(this.ps, objet);
            this.executeUpdate();
            System.out.println("Article mis à jour avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'article : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Article> selectAll() {
        List<Article> articles = new ArrayList<>();
        ResultSet rs = null;
        try {
            this.connect();
            this.init(SELECT_ALL_QUERY);
            rs = this.ps.executeQuery();
            while (rs.next()) {
                articles.add(convertToObject(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection de tous les articles : " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return articles;
    }

    @Override
    public Article selectById(int id) {
        String selectByIdQuery = String.format(SELECT_BY_ID_QUERY_TEMPLATE, tableName);
        ResultSet rs = null;
        try {
            this.connect();
            this.init(selectByIdQuery);
            this.ps.setInt(1, id);
            rs = this.ps.executeQuery();
            if (rs.next()) {
                return convertToObject(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection de l'article par ID : " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void remove(int id) {
        String deleteQuery = String.format(DELETE_QUERY_TEMPLATE, tableName);
        try {
            this.connect();
            this.init(deleteQuery);
            this.ps.setInt(1, id);
            this.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'article : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateQuantite(Article article) {
        try {
            this.connect();
            this.init(UPDATE_QUANTITY_QUERY);
            this.ps.setInt(1, article.getQuantityInStock());
            this.ps.setInt(2, article.getId());
            this.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la quantité de l'article : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void activerArticle(Article article) {
        try {
            this.connect();
            this.init(ACTIVE_ARTICLE_QUERY);
            this.ps.setInt(1, article.getId());
            this.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'activation de l'article : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void desactiverArticle(Article article) {
        try {
            this.connect();
            this.init(DESACTIVE_ARTICLE_QUERY);
            this.ps.setInt(1, article.getId());
            this.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la désactivation de l'article : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Article> getDesactiverArticle() {
        List<Article> articles = new ArrayList<>();
        ResultSet rs = null;
        try {
            this.connect();
            this.init(LISTE_DESACTIVE_QUERY);
            rs = this.ps.executeQuery();
            while (rs.next()) {
                articles.add(convertToObject(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des articles désactivés : " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return articles;
    }

    @Override
    protected void setInsertParameters(PreparedStatement pstmt, Article objet) throws SQLException {
        pstmt.setString(1, objet.getLibelle());
        pstmt.setDouble(2, objet.getPrix());
        pstmt.setString(3, objet.getReference());
        pstmt.setInt(4, objet.getQuantityInStock());
    }

    public void setUpdateParameters(PreparedStatement pstmt, Article objet) throws SQLException {
        pstmt.setString(1, objet.getLibelle());
        pstmt.setDouble(2, objet.getPrix());
        pstmt.setString(3, objet.getReference());
        pstmt.setInt(4, objet.getQuantityInStock());
        pstmt.setInt(5, objet.getId());
    }

    @Override
    public Article convertToObject(ResultSet rs) throws SQLException {
        Article article = new Article();
        article.setId(rs.getInt("id"));
        article.setLibelle(rs.getString("libelle"));
        article.setPrix(rs.getDouble("prix"));
        article.setReference(rs.getString("reference"));
        article.setQuantityInStock(rs.getInt("quantityInStock"));
        return article;
    }

    @Override
    public Article findby(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findby'");
    }

    @Override
    public String generateSQL() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateSQL'");
    }

    @Override
    public void setField(Article entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setField'");
    }

    @Override
    public void selectByTel(String tel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectByTel'");
    }

    @Override
    public void save(Article article) {
        String insertQuery = "INSERT INTO article (libelle, prix, reference) VALUES (?, ?, ?)";
        try {
            this.connect();
            this.init(insertQuery);
            this.ps.setString(1, article.getLibelle());
            this.ps.setDouble(2, article.getPrix());
            this.ps.setString(3, article.getReference());
            this.ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion de l'article : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
public Article findById(int id) {
    String selectByIdQuery = String.format(SELECT, tableName);
    ResultSet rs = null;
    try {
        this.connect();
        this.init(selectByIdQuery);
        this.ps.setInt(1, id);
        rs = this.ps.executeQuery();
        if (rs.next()) {
            return convertToObject(rs);
        }
    } catch (SQLException e) {
        System.out.println("Erreur lors de la sélection de l'article par ID : " + e.getMessage());
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
            this.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return null;

}

  
}
