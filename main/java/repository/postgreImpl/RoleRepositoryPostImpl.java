package repository.postgreImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataSource.DataBasePostgreImpl;
import core.RepoInter.ArticleRepository;
import core.RepoInter.RoleRepository;
import entities.Article;
import entities.Dette;
import entities.Role;
import entities.User;

public class RoleRepositoryPostImpl extends DataBasePostgreImpl<Role> implements RoleRepository {

    private static final String INSERT_QUERY = "INSERT INTO role (name) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE role SET name = ? WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM role";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM role WHERE id = ?";
    @Override
    public void insert(Role objet) {
        try {
            this.connect();
            this.init(INSERT_QUERY);
            setInsertParameters(this.ps, objet);
            this.executeUpdate();
            ResultSet rs = this.ps.getGeneratedKeys();
            if (rs.next()) {
                objet.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion de l'utilisateur : " + e.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Role objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<Role> selectAll() {
         List<Role> listRoles = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            connect();
            this.init(SELECT_ALL_QUERY);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                listRoles.add(convertToObject(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
        } finally {
            closeResources(resultSet);
        }
        return listRoles;
    }

    @Override
    public Role selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    @Override
    public void remove(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public Role findby(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findby'");
    }

    @Override
    public String generateSQL() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateSQL'");
    }

    @Override
    public void setField(Role entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setField'");
    }

    @Override
    public void save(Role objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    protected void setInsertParameters(PreparedStatement pstmt, Role objet) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setInsertParameters'");
    }

    @Override
    public Role convertToObject(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setId(rs.getInt("id"));
        role.setNom(rs.getString("nom"));
        return role;
    }
    private void closeResources(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
