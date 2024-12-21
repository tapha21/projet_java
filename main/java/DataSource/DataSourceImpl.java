package DataSource;

import java.sql.*;

public class DataSourceImpl<T> implements DataSource <T>   {
    private final String url="jdbc:mysql://localhost:3306/ges_dette";
    private final String user="root";
    private final String password="";
    protected PreparedStatement ps;
    protected Connection conn = null;
    @Override
    public void connect() throws SQLException {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        conn=DriverManager.getConnection(url, user, password);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }          
    }

    @Override
    public void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            System.out.println("Connexion fermée");
        }
        
    
    }

  
    @Override
    public int executeUpdate() throws SQLException {
        return  ps.executeUpdate();
    }
    // public ResultSet=

    @Override
    public String generateSQL() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateSQL'");
    }

    @Override
    public void setField(T entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setField'");
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
          return  ps.executeQuery();
    }

    @Override
    public void init(String sql) throws SQLException {
        this.connect();
        if (sql.toUpperCase().trim().startsWith("INSERT")) {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }else{
            ps=conn.prepareStatement(sql);
        }


    }
    
}
