package repository.postgreImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DataSource.BaseRepositoryDB;
import DataSource.DataBasePostgreImpl;
import core.RepoInter.ClientRepository;
import core.RepoInter.UserRepository;
import entities.Client;
import entities.User; // Assurez-vous d'importer la classe User

public class ClientRepositoryPostgreImpl extends DataBasePostgreImpl<Client> implements ClientRepository {
    UserRepository userRepository;
    protected String tableName;

    public ClientRepositoryPostgreImpl(UserRepository userRepository) {
        this.tableName = "client"; 
        this.userRepository = userRepository;
    }

    private static final String INSERT_QUERY_TEMPLATE = "INSERT INTO %s (surname, telephone, adresse, user_id) VALUES (?, ?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT client.id AS client_id, client.surname AS client_surname, "
               + "client.telephone AS client_telephone, client.adresse AS client_adresse, "
               + "\"user\".nom AS user_nom, \"user\".prenom AS user_prenom "
               + "FROM \"%s\" client "
               + "LEFT JOIN \"user\" ON client.user_id = \"user\".id";
    // private static final String SELECT_QUERY = "select * from client";

    private static final String SELECT_CLIENT_QUERY_TEMPLATE = "SELECT c.id AS client_id, c.nom AS client_nom, c.prenom AS client_prenom, "
            + "c.telephone AS client_telephone, c.adresse AS client_adresse, "
            + "u.id AS user_id, u.nom AS user_nom, u.prenom AS user_prenom, "
            + "u.login AS user_login, u.password AS user_password, r.nom AS role_nom "
            + "FROM %s c "
            + "LEFT JOIN \"user\" u ON c.user_id = u.id "
            + "LEFT JOIN role r ON u.role_id = r.id "
            + "WHERE c.telephone = ?";
    private static final String selectTel = "SELECT * FROM client WHERE telephone = ?";
    private static final String selectSurname = "SELECT * FROM client WHERE surname = ?";
    private static final String deleteQuery = "DELETE FROM client WHERE id = ?";
    private static final String FILTER_CLIENTS_BY_ACCOUNT = "SELECT * FROM client WHERE user_id IS NOT NULL";


    @Override
    public void insert(Client client) {
        String insertQuery = String.format(INSERT_QUERY_TEMPLATE, tableName);
        try {
            this.connect();
            this.init(insertQuery);
            setInsertParameters(this.ps, client);
            this.executeUpdate();
            ResultSet rs = this.ps.getGeneratedKeys();
            if (rs.next()) {
                client.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion du client : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Client> selectAll() {
        List<Client> listClients = new ArrayList<>();
        String selectQuery = String.format(SELECT_QUERY, this.tableName);
        ResultSet resultSet = null;
        try {
            connect();
            this.init(selectQuery);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                listClients.add(convertToObject(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
            try {
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listClients;
    }

    @Override
    public Client selectByTel(String telephone) {
        Client client = null;
        String selectClientQuery = String.format(selectTel, tableName);
        try {
            connect();
            this.init(selectClientQuery);
            ps.setString(1, telephone);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                client = FIND(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du client : " + e.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    @Override
    protected void setInsertParameters(PreparedStatement pstmt, Client client) throws SQLException {
        pstmt.setString(1, client.getSurname());
        pstmt.setString(2, client.getTelephone());
        pstmt.setString(3, client.getAdresse());
        if (client.getUser() != null) {
            pstmt.setInt(4, client.getUser().getId());
        } else {
            pstmt.setNull(4, java.sql.Types.INTEGER);
        }
    }

    @Override
    public Client convertToObject(ResultSet resultSet) throws SQLException {
        Client client = new Client(); 
        int clientId = resultSet.getInt("client_id");
        String nomClient = resultSet.getString("client_surname");
        String telephone = resultSet.getString("client_telephone");
        String adresse = resultSet.getString("client_adresse");
        User user = new User();
        user.setNom(resultSet.getString("user_nom"));
        user.setPrenom(resultSet.getString("user_prenom"));
        client.setUser(user);
        client.setId(clientId);
        client.setSurname(nomClient);
        client.setTelephone(telephone);
        client.setAdresse(adresse);
        client.setUser(user);        
        return client;
    }
    @Override
    public void update(Client client) {
        String updateQuery = "UPDATE client SET surname = ?, telephone = ?, adresse = ?, user_id = ? WHERE id = ?";
    try {
        this.connect();
        this.init(updateQuery);
        this.ps.setString(1, client.getSurname());
        this.ps.setString(2, client.getTelephone());
        this.ps.setString(3, client.getAdresse());
        if (client.getUser() != null) {
            this.ps.setInt(4, client.getUser().getId());
        } else {
            this.ps.setNull(4, java.sql.Types.INTEGER);
        }
        this.ps.setInt(5, client.getId());
        this.executeUpdate();
    } catch (SQLException e) {
        System.out.println("Erreur lors de la mise à jour du client : " + e.getMessage());
    } finally {
        try {
            this.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }

    @Override
    public Client selectById(int id) {
        Client client = null;
    try {
        this.connect();
        this.init(selectTel);
        this.ps.setInt(1, id);
        ResultSet resultSet = this.ps.executeQuery();
        if (resultSet.next()) {
            client = convertToObject(resultSet);
        }
    } catch (SQLException e) {
        System.out.println("Erreur lors de la sélection du client par ID : " + e.getMessage());
    } finally {
        try {
            this.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return client;
    }

    @Override
    public Client selectBySurname(String name) {
        Client client = null;
        try {
            this.connect();
            this.init(selectSurname);
            this.ps.setString(1, name);
            ResultSet resultSet = this.ps.executeQuery();
            if (resultSet.next()) {
                client = convertToObject(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection du client par nom : " + e.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return client;
    }


    @Override
    public Client findby(int id) {
        return selectById(id);
    }

    @Override
    public String generateSQL() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateSQL'");
    }

    @Override
    public void setField(Client entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setField'");
    }

    @Override
    public void remove(int id) {
    try {
        this.connect();
        this.init(deleteQuery);
        this.ps.setInt(1, id);
        this.executeUpdate();
    } catch (SQLException e) {
        System.out.println("Erreur lors de la suppression du client : " + e.getMessage());
    } finally {
        try {
            this.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }

    public Client FIND(ResultSet resultSet) throws SQLException {
        Client client = new Client(); 
        int clientId = resultSet.getInt("Id");
        String nomClient = resultSet.getString("surname");
        String telephone = resultSet.getString("telephone");
        String adresse = resultSet.getString("adresse");

        client.setId(clientId);
        client.setSurname(nomClient);
        client.setTelephone(telephone);
        client.setAdresse(adresse);
        // client.setUser(user);
        return client;
    }
    public List<Client> filterClientsByAccount() {
        List<Client> clients = new ArrayList<>();
        try {
            connect();
            this.init(FILTER_CLIENTS_BY_ACCOUNT);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                clients.add(convertToObject(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des clients : " + e.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return clients;
    }

    public Client findByUser(User user) {
        Client client = null;
        String query = "SELECT * FROM clients WHERE user_id = ?";
        
        try {
            connect();
            this.init(query);
            ps.setLong(1, user.getId());
            ResultSet resultSet = ps.executeQuery();
            
            if (resultSet.next()) {
                client = FIND(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du client : " + e.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return client;
    }
    
}
