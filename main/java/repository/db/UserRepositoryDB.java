package repository.db;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DataSource.BaseRepositoryDB;
import core.config.repoImpl.RepositoryDBImpl;
import core.RepoInter.UserRepository;
import entities.Client;
import entities.User;
import enums.*;

public class UserRepositoryDB extends BaseRepositoryDB<User> implements UserRepository{
    public UserRepositoryDB(){
        this.tableName="User";
    }
    @Override
    public void insert(User objet) {
        int nbre = 0;
        try {
            String sql = String.format("INSERT INTO %s (`login`, `prenom`, `nom`, `password`, `etat`, `role`) VALUES (?, ?, ?, ?, 'Activer', ?)", this.tableName);
            this.connect();
            this.init(sql);
            
            this.ps.setString(1, objet.getLogin());
            this.ps.setString(2, objet.getPrenom());
            this.ps.setString(3, objet.getNom());
            this.ps.setString(4, objet.getPassword());
            // this.ps.setString(5, objet.getRole()); 
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
    
    

    @Override
    public void update(User objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<User> selectAll() {
     List<User> users = new ArrayList<>();
        try {
                String sql = String.format("SELECT * FROM %s  ",this.tableName);
            
            this.connect();
            this.init(sql);
            // Exécution de la requête
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                users.add(this.convertToObject(rs));
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
        return users
        ;
    }
    @Override
    public List<User> selectByRole(String role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectByRole'");
    }

    @Override
    public List<User> selectActive(boolean active) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectActive'");
    }

    @Override
    public User selectByID(int id) {
        User user=new User();
        try {
            String sql = String.format("SELECT * FROM User WHERE id = ?", this.tableName);
            this.connect();
            this.init(sql);
            this.ps.setInt(1, id);
            ResultSet rs = this.executeQuery();
            if (rs.next()) {
                user = this.convertToObject(rs);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
    

    @Override
    public List search(String tel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    @Override
    public User convertToObject(ResultSet rs) throws SQLException {
        User user = new User();
        user.setNom(rs.getString("nom"));
        user.setPrenom(rs.getString("prenom"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setEtat(rs.getBoolean("etat")); 
        // user.setRole(Role.valueOf(rs.getString("role")));  
        return user;
    }

    @Override
    public User selectByLoginAndPassword(String login, String password) {
       User user=null;
       try {
        String sql = String.format("select * from User where login like ? and password like ?", this.tableName);
        this.connect();
        this.init(sql);
        this.ps.setString(1,login ); 
        this.ps.setString(2, password);
        ResultSet rs=this.executeQuery();
        if(rs.next()){
            user=this.convertToObject(rs);
        }
        rs.close();
       } catch (Exception e) {
        // TODO: handle exception
       }finally {
        try {
            this.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return user;
    }
    //                                  ---------     Factory       -------

    @Override
    protected String getSelectAllSQL() {
        return String.format("SELECT * FROM %s", this.tableName);
    }
    @Override
    protected String getInsertSQL() {
        return String.format("INSERT INTO %s (`login`, `prenom`, `nom`, `password`, `etat`, `role`) VALUES (?, ?, ?, ?, 'Activer', ?)", this.tableName);

    }
    @Override
    public User selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }
    @Override
    protected void setInsertParameters(PreparedStatement pstmt, User objet) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setInsertParameters'");
    }

    @Override
    public User findby(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findby'");
    }
    @Override
    public void remove(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }
    @Override
    public String getRoleByLogin(String login) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRoleByLogin'");
    }
    
  
    
    }



  

