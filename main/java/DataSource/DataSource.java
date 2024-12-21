package DataSource;
import java.sql.*;
public interface DataSource <T>  {
    void connect() throws SQLException; 
    void closeConnection() throws SQLException; 
    ResultSet executeQuery() throws SQLException; 
    int executeUpdate() throws SQLException;
    void init(String sql)throws SQLException;
    String generateSQL(); 
    void setField(T entity);
    
} 