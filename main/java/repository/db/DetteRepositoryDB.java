package repository.db;

import DataSource.BaseRepositoryDB;
import core.RepoInter.DetteRepository;
import entities.Dette;
import entities.Article;
import entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetteRepositoryDB extends BaseRepositoryDB<Dette> implements DetteRepository {

    public DetteRepositoryDB() {
        this.tableName = "dette";
    }

    @Override
    public void insert(Dette objet) {
        int nbre = 0;
        try {
            String sql = String.format("INSERT INTO %s (`date`, `montantVerser`, `reglee`, `id_client`, `montant`) VALUES (?, ?, ?, ?, ?)", this.tableName);
            this.connect();
            this.init(sql);
            this.ps.setTimestamp(1, Timestamp.valueOf(objet.getDate()));
            this.ps.setDouble(2, objet.getMontantVerser());
            this.ps.setBoolean(3, objet.isReglee()); 
            this.ps.setInt(4, objet.getClient().getId()); // Supposant que client est un objet de type Client
            this.ps.setDouble(5, objet.getMontantRestant());

            nbre = this.ps.executeUpdate();
            if (nbre > 0) {
                ResultSet rs = this.ps.getGeneratedKeys();
                if (rs.next()) {
                    objet.setId(rs.getInt(1)); // Assurez-vous que le setter est présent dans l'entité Dette
                }
                System.out.println("Insertion réussie et connexion BD établie");
            }
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

    @Override
    public void update(Dette objet) {
        // Implémentez la logique de mise à jour ici
        // Par exemple :
        // String sql = String.format("UPDATE %s SET ... WHERE id = ?", this.tableName);
    }

    @Override
    public Dette selectById(int id) {
        Dette dette = null;
        try {
            String sql = String.format("SELECT * FROM %s WHERE id = ?", this.tableName);
            this.connect();
            this.init(sql);

            this.ps.setInt(1, id);
            ResultSet rs = this.ps.executeQuery();
            if (rs.next()) {
                dette = this.convertToObject(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dette;
    }
    @Override
    public List<Dette> selectAll() {
        List<Dette> dettes = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM %s", this.tableName);
            this.connect();
            this.init(sql);

            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                dettes.add(this.convertToObject(rs));
            }
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
        return dettes;
    }

    @Override
    public Dette convertToObject(ResultSet rs) throws SQLException {
        Dette dette = new Dette();
        dette.setId(rs.getInt("id"));
        dette.setDate(rs.getTimestamp("date").toLocalDateTime());
        dette.setMontantVerser(rs.getDouble("montantVerser"));
        dette.setMontantRestant(rs.getDouble("montant")); // Assurez-vous que le setter est présent dans l'entité Dette
        dette.setReglee(rs.getBoolean("reglee"));

        // Récupération du client associé (si nécessaire)
        Client client = new Client(); // Créez une instance de Client
        client.setId(rs.getInt("id_client")); // Assurez-vous d'avoir l'ID du client
        // Vous pourriez vouloir faire une recherche pour obtenir le client complet depuis votre ClientRepository
        dette.setClient(client);

        return dette;
    }

    @Override
    protected String getInsertSQL() {
        return String.format("INSERT INTO %s (`date`, `montantVerser`, `reglee`, `id_client`, `montant`) VALUES (?, ?, ?, ?, ?)", this.tableName);
    }

    @Override
    protected String getSelectAllSQL() {
        return String.format("SELECT * FROM %s", this.tableName);
    }

    @Override
    protected void setInsertParameters(PreparedStatement pstmt, Dette objet) throws SQLException {
        this.ps.setTimestamp(1, Timestamp.valueOf(objet.getDate()));
        pstmt.setDouble(2, objet.getMontantVerser());
        pstmt.setBoolean(3, objet.isReglee());
        pstmt.setInt(4, objet.getClient().getId());
        pstmt.setDouble(5, objet.getMontantRestant());
    }

    @Override
    public Dette findby(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findby'");
    }

    @Override
    public void selectByTel(String tel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectByTel'");
    }

    @Override
    public void remove(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public void save(Dette dette) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Dette findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Dette> findByClient(Client client) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByClient'");
    }

    @Override
    public  List<Dette> NonSolde(Client client) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'solde'");
    }

    @Override
    public List<Dette> findByEtat(boolean etat) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByEtat'");
    }

  
}
