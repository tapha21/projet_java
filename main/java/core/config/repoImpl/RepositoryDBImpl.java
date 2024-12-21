package core.config.repoImpl;
import DataSource.DataSourceImpl;
import core.config.Repository;

import java.sql.*;
public abstract class RepositoryDBImpl<T> extends DataSourceImpl<T>  implements Repository <T>{

    protected String  tableName;
    public abstract T convertToObject(ResultSet rs) throws SQLException;
    
}
