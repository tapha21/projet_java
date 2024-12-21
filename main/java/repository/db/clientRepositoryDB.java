package repository.db;
import DataSource.BaseRepositoryDB;
import core.RepoInter.ClientRepository;
import core.RepoInter.UserRepository;
import entities.Client;
import entities.User;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
public class clientRepositoryDB extends BaseRepositoryDB<Client>  implements ClientRepository{

    UserRepository userRepositoryDB;
    // public clientRepositoryDB(UserRepository userRepository){
    //     this.tableName = "Client";
    //     this.userRepositoryDB = userRepository;
    // }
    public clientRepositoryDB(UserRepository userRepository){
        this.tableName = "Client";
        this.userRepositoryDB = userRepository;
    }
    
    @Override
    public void insert(Client objet) {
        int nbre = 0;
        User user = objet.getUser();
        try {
            if (user != null) {
                userRepositoryDB.insert(user);
                user.setId(getLastInsertedId());
            }
            String sql = String.format("INSERT INTO %s (`surname`, `telephone`, `adresse`, `user_id`) VALUES (?, ?, ?, ?)", this.tableName);
            this.connect();
            this.init(sql);
            this.ps.setString(1, objet.getSurname());
            this.ps.setString(2, objet.getTelephone());
            this.ps.setString(3, objet.getAdresse());
    
            if (user != null && user.getId() > 0) {
                this.ps.setInt(4, user.getId());
            } else {
                this.ps.setNull(4, Types.INTEGER);
            }
    
            nbre = this.executeUpdate();
    
            ResultSet rs = this.ps.getGeneratedKeys();
            if (rs.next()) {
                objet.setId(rs.getInt(1));
            }
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
    


    @Override
    public void update(Client objet) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<Client> selectAll() {
        List<Client> clients = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM  %s ",this.tableName);
            this.connect();
            this.init(sql);
            
            // Exécution de la requête
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                clients.add(this.convertToObject(rs));
            }
            rs.close();
            System.out.println("Connexion BD établie");
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return clients;
    }

    public Client selectByTel(String tel) {
        Client client = null;
        try {
            String sql = String.format("SELECT * FROM  %s WHERE telephone = ?",this.tableName);
            this.connect();
            this.init(sql);  // Utilisation de `init` pour initialiser `ps`
            
            // Remplacement du paramètre dans la requête SQL
            this.ps.setString(1, tel);
            
            // Exécution de la requête
            ResultSet rs = this.ps.executeQuery();
            if (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("id"));
                client.setSurname(rs.getString("surname"));
                client.setTelephone(rs.getString("telephone"));
                client.setAdresse(rs.getString("adresse"));
            }
            rs.close();
            System.out.println("Client trouvé, connexion BD établie");
        } catch (SQLException e) {
            e.printStackTrace();
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
    public Client convertToObject(ResultSet rs) throws SQLException {
            Client client = new Client();
            client.setId(rs.getInt("id"));
            client.setSurname(rs.getString("surname"));
            client.setTelephone(rs.getString("telephone"));
            client.setAdresse(rs.getString("adresse"));
            int id=rs.getInt("user_id");
            User user = this.userRepositoryDB.selectByID(id);
            client.setUser(user);
            return client;
        }

     //   ---------- Factory-  -------
    @Override
    protected String getInsertSQL() {
        return String.format("INSERT INTO %s (`surname`, `telephone`, `adresse`, `user_id`) VALUES (?, ?, ?, ?)", this.tableName);
    }

    @Override
    protected String getSelectAllSQL() {
        return String.format("SELECT * FROM %s", this.tableName);
    }

    @Override
 protected void setInsertParameters(PreparedStatement pstmt, Client objet) throws SQLException {
    pstmt.setInt(1, objet.getId());
    pstmt.setString(2, objet.getSurname());
    pstmt.setString(3, objet.getTelephone());
    pstmt.setString(4, objet.getAdresse());
    
    if (objet.getUser() != null) {
        pstmt.setInt(5, objet.getUser().getId());
    } else {
        pstmt.setNull(5, java.sql.Types.INTEGER);
    }
}

    public int getLastInsertedId() {
        int lastId = 0;
        String sql = "SELECT LAST_INSERT_ID()";
        try {
            this.connect();
            this.init(sql);
            ResultSet rs = this.executeQuery();
            if (rs.next()) {
                lastId = rs.getInt(1);
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
        return lastId;
    }

    @Override
    public Client selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    @Override
    public Client selectBySurname(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectBySurname'");
    }

    

    @Override
    public Client findby(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findby'");
    }

    @Override
    public void remove(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public List<Client> filterClientsByAccount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filterClientsByAccount'");
    }

    @Override
    public Client findByUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUser'");
    }
      
    
}