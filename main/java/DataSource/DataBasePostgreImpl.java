package DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DataBasePostgreImpl<T> implements DataSource<T> {
    // URL de connexion pour PostgreSQL
    private final String URL = "jdbc:postgresql://localhost:5432/ges_dette"; 
    private final String USER = "postgres";
    private final String PASSWORD = "Taphatall01";
    protected Connection connection = null;
    protected PreparedStatement ps = null;

    @Override
    public void connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC PostgreSQL non trouvé.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données.");
            e.printStackTrace();
            throw e; // Propagation de l'exception
        }
    }

    @Override
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion.");
                e.printStackTrace();
                throw e; // Propagation de l'exception
            }
        }
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        if (ps == null) {
            throw new SQLException("PreparedStatement n'est pas initialisé.");
        }
        return ps.executeQuery();
    }

    @Override
    public int executeUpdate() throws SQLException {
        if (ps == null) {
            throw new SQLException("PreparedStatement n'est pas initialisé.");
        }
        return ps.executeUpdate();
    }

    @Override
    public void init(String sql) throws SQLException {
        String sqlUpperCase = sql.toUpperCase().trim();

        if (sqlUpperCase.startsWith("INSERT")) {
            ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        } else {
            ps = connection.prepareStatement(sql);
        }
    }

    protected abstract void setInsertParameters(PreparedStatement pstmt, T objet) throws SQLException;

    public abstract T convertToObject(ResultSet rs) throws SQLException;
}

