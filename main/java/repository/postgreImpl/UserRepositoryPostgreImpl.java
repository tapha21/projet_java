package repository.postgreImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import enums.*;
import DataSource.DataBasePostgreImpl;
import core.RepoInter.UserRepository;
import entities.User;
import entities.Role; 

public class UserRepositoryPostgreImpl extends DataBasePostgreImpl<User> implements UserRepository {
    
    private final String tableName = "users";
    private final String INSERT_QUERY = "INSERT INTO \"users\" (nom, prenom, login, password, etat, role_id) VALUES (?, ?, ?, ?, ?, ?)";
    private final String SELECT_ALL_QUERY = "SELECT " +
    "\"user\".id AS user_id, " +
    "\"user\".prenom AS user_prenom, " +
    "\"user\".nom AS user_nom, " +
    "\"user\".login AS user_login, " +
    "\"user\".password AS user_password, " +
    "\"user\".etat AS user_etat, " +
    "role.id AS role_id, " +
    "role.nom AS role_nom " +
    "FROM \"user\" " +
    "JOIN role ON \"user\".role_id = role.id;";

    private final String SELECT_BY_ROLE_QUERY = "SELECT " +
    "\"user\".id AS user_id, " +
    "\"user\".prenom AS user_prenom, " +
    "\"user\".nom AS user_nom, " +
    "\"user\".login AS user_login, " +
    "\"user\".password AS user_password, " +
    "\"user\".etat AS user_etat, " +
    "role.id AS role_id, " +
    "role.nom AS role_nom " +
    "FROM \"user\" " +
    "JOIN role ON \"user\".role_id = role.id " +
    "WHERE role.nom = ?;";
    private final String SELECT_BY_ID_QUERY = "SELECT * FROM \"user\" WHERE id = ?;";
    private final String SELECT_BY_LOGIN_QUERY = "SELECT * FROM \"users\" WHERE login = ? and password = ?;";
    private final String UPDATE_QUERY = "UPDATE \"user\" SET nom = ?, prenom = ?, login = ?, password = ?, etat = ? WHERE id = ?;";
    private final String DELETE_QUERY = "DELETE FROM \"user\" WHERE id = ?;";

    @Override
    public void insert(User user) {
        try {
            this.connect();
            this.init(INSERT_QUERY);
            setInsertParameters(this.ps, user);
            this.executeUpdate();
            ResultSet rs = this.ps.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
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
    public List<User> selectAll() {
        List<User> listUsers = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            connect();
            this.init(SELECT_ALL_QUERY);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                listUsers.add(convertToObject(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
        } finally {
            closeResources(resultSet);
        }
        return listUsers;
    }

    @Override
    public User selectById(int id) {
        User user = null;
        try {
            connect();
            this.init(SELECT_BY_ID_QUERY);
            this.ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                user = convertToObject(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur par ID : " + e.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public User selectByLoginAndPassword(String Login, String password) {
        User user = null;
        try {
            connect();
            this.init(SELECT_BY_LOGIN_QUERY);
            this.ps.setString(1, Login);
            this.ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                user = convertToObject(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur par login : " + e.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public void update(User user) {
        try {
            this.connect();
            this.init(UPDATE_QUERY);
            setUpdateParameters(this.ps, user);
            this.ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
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
    public List<User> selectActive(boolean active) {
        List<User> listUsers = new ArrayList<>();
        String sql = "SELECT * FROM \"user\" WHERE etat = ?;";
        try {
            connect();
            this.init(sql);
            this.ps.setBoolean(1, active);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                listUsers.add(convertToObject(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche des utilisateurs actifs : " + e.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return listUsers;
    }

    @Override
    public User convertToObject(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));       
        user.setNom(resultSet.getString("nom"));   
        user.setPrenom(resultSet.getString("prenom"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setEtat(resultSet.getBoolean("etat"));      
        Role role = new Role();
        role.setId(resultSet.getInt("roles_id"));
        role.setNom(resultSet.getString("nom"));
        user.setRole(role);
        return user;
    }

    @Override
    protected void setInsertParameters(PreparedStatement pstmt, User user) throws SQLException {
        pstmt.setString(1, user.getNom());
        pstmt.setString(2, user.getPrenom());
        pstmt.setString(3, user.getLogin());
        pstmt.setString(4, user.getPassword());
        pstmt.setBoolean(5, user.isEtat());
        pstmt.setInt(6, user.getRole().getId());
    }

    protected void setUpdateParameters(PreparedStatement pstmt, User user) throws SQLException {
        pstmt.setString(1, user.getNom());
        pstmt.setString(2, user.getPrenom());
        pstmt.setString(3, user.getLogin());
        pstmt.setString(4, user.getPassword());
        pstmt.setBoolean(5, user.isEtat());
        pstmt.setInt(6, user.getId());
    }

    @Override
    public List<User> search(String tel) {
        List<User> listUsers = new ArrayList<>();
    String sql = "SELECT * FROM \"users\" WHERE tel = ?;";
    ResultSet resultSet = null;
    try {
        connect();
        this.init(sql);
        this.ps.setString(1, tel);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            listUsers.add(convertToObject(resultSet));
        }
    } catch (SQLException e) {
        System.out.println("Erreur lors de la recherche par téléphone : " + e.getMessage());
    } finally {
        closeResources(resultSet);
    }
    return listUsers;
    }

    @Override
    public User selectByID(int id) {
        return selectById(id);
    }

    @Override
    public User findby(int id) {
        return selectById(id);
    }

    @Override
    public String generateSQL() {
        // Méthode pour générer SQL dynamique si nécessaire (exemple)
        throw new UnsupportedOperationException("Unimplemented method 'generateSQL'");
    }

    @Override
    public void setField(User entity) {
        // Implémentation pour définir des champs spécifiques (à personnaliser)
        throw new UnsupportedOperationException("Unimplemented method 'setField'");
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

    @Override
    public List<User> selectByRole(String roleName) {
        List<User> listUsers = new ArrayList<>();
    ResultSet resultSet = null;
    try {
        connect();
        this.init(SELECT_BY_ROLE_QUERY);
        this.ps.setString(1, roleName);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            listUsers.add(convertToObject(resultSet));
        }
    } catch (SQLException e) {
        System.out.println("Erreur lors de la recherche par rôle : " + e.getMessage());
    } finally {
        closeResources(resultSet);
    }
    return listUsers;
    }

    @Override
    public void remove(int id) {
        try {
            this.connect();
            this.init(DELETE_QUERY);
            this.ps.setInt(1, id);
            this.ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getRoleByLogin(String login) {
        String role = null;  // Variable pour stocker le rôle récupéré
        String query = "SELECT r.nom AS role FROM users u JOIN role r ON u.roles_id = r.id WHERE u.login = ?";
    
        try {
            this.connect();  // Se connecter à la base de données (assurez-vous que connect() ouvre une connexion)
            this.init(query);  // Initialiser le PreparedStatement avec la requête
    
            // Préparer la requête et définir les paramètres
            this.ps.setString(1, login);
    
            // Exécuter la requête et récupérer les résultats
            try (ResultSet rs = this.ps.executeQuery()) {
                if (rs.next()) {
                    role = rs.getString("role");  // Récupérer le rôle du résultat
                } else {
                    throw new SQLException("Aucun rôle trouvé pour le login donné.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du rôle : " + e.getMessage());
        } finally {
            try {
                closeConnection();  // Assurez-vous que la connexion est fermée après l'opération
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    
        return role;  // Retourner le rôle (peut être null si une erreur s'est produite)
    }
    
}
