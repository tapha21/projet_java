package repository.postgreImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import DataSource.DataBasePostgreImpl;
import core.RepoInter.PaiementRepository;
import entities.Paiement;

public class PaiementRepositoryPostImpl extends DataBasePostgreImpl<Paiement> implements PaiementRepository {

    protected String tableName;

    public PaiementRepositoryPostImpl() {
        this.tableName = "paiements";
    }

    @Override
    public void insert(Paiement paiement) {
        String sql = "INSERT INTO paiements (montant, date, id_dette) VALUES (?, ?, ?)";
        try {
            connect();
            this.init(sql);
            this.ps.setDouble(1, paiement.getMontant());
            if (paiement.getDate() != null) {
                this.ps.setTimestamp(2, java.sql.Timestamp.valueOf(paiement.getDate()));
            } else {
                this.ps.setTimestamp(2, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now())); // Utilisation de la date actuelle
            }            this.ps.setInt(3, paiement.getDette().getId());
            this.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion du paiement : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Paiement paiement) {
        String sql = "UPDATE paiements SET montant = ?, date = ?, id_dette = ? WHERE id = ?";
        try {
            connect();
            this.init(sql);
            this.ps.setDouble(1, paiement.getMontant());
            this.ps.setTimestamp(2, java.sql.Timestamp.valueOf(paiement.getDate()));
            this.ps.setInt(3, paiement.getDette().getId());
            this.ps.setInt(4, paiement.getId());
            this.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du paiement : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Paiement> selectAll() {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiements";
        try {
            connect();
            this.init(sql);
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                paiements.add(convertToObject(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des paiements : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return paiements;
    }

    @Override
    public Paiement selectById(int id) {
        Paiement paiement = null;
        String sql = "SELECT * FROM paiements WHERE id = ?";
        try {
            connect();
            this.init(sql);
            this.ps.setInt(1, id);
            ResultSet rs = this.ps.executeQuery();
            if (rs.next()) {
                paiement = convertToObject(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection du paiement par ID : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return paiement;
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM paiements WHERE id = ?";
        try {
            connect();
            this.init(sql);
            this.ps.setInt(1, id);
            this.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du paiement : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Paiement convertToObject(ResultSet resultSet) throws SQLException {
        Paiement paiement = new Paiement();
        paiement.setId(resultSet.getInt("id"));
        paiement.setMontant(resultSet.getDouble("montant"));
        paiement.setDate(resultSet.getTimestamp("date").toLocalDateTime());
        // Assurez-vous que la relation entre Paiement et Dette est bien configurée
        // paiement.setDette(detteRepository.selectById(resultSet.getInt("id_dette")));
        return paiement;
    }

    @Override
    public Paiement findby(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findby'");
    }

    @Override
    public String generateSQL() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateSQL'");
    }

    @Override
    public void setField(Paiement entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setField'");
    }

    @Override
    public void save(Paiement paiement) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Paiement findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Paiement> findByDette(int detteId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByDette'");
    }

    @Override
    protected void setInsertParameters(PreparedStatement pstmt, Paiement objet) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setInsertParameters'");
    }
}
