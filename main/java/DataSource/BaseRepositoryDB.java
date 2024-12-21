package DataSource;
import java.util.List;

import core.config.repoImpl.RepositoryDBImpl;

import java.sql.*;
import java.util.ArrayList;

public abstract class BaseRepositoryDB<T> extends RepositoryDBImpl<T> {

    // Méthode générique pour insérer un objet
    public void insert(T objet) {
        int nbre = 0;
        try {
            String sql = getInsertSQL();
            this.connect();
            this.init(sql);
            setInsertParameters(this.ps, objet);  // Méthode abstraite
            nbre = this.ps.executeUpdate();
            System.out.println("Insertion réussie et connexion BD établie");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Méthode générique pour sélectionner tous les objets
    public List<T> selectAll() {
        List<T> list = new ArrayList<>();
        try {
            String sql = getSelectAllSQL();
            this.connect();
            this.init(sql);
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                list.add(convertToObject(rs));  // Méthode abstraite
            }
            rs.close();
            System.out.println("Connexion BD établie");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // Méthode à implémenter pour obtenir la requête d'insertion spécifique à l'entité
    protected abstract String getInsertSQL();

    // Méthode à implémenter pour obtenir la requête de sélection spécifique
    protected abstract String getSelectAllSQL();

    // Méthode à implémenter pour définir les paramètres d'insertion
// Méthode abstraite définie dans une classe générique
protected abstract void setInsertParameters(PreparedStatement pstmt, T objet) throws SQLException;

    // Méthode à implémenter pour convertir un ResultSet en objet
    public abstract T convertToObject(ResultSet rs) throws SQLException;
}
