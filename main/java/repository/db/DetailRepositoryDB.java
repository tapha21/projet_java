package repository.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DataSource.BaseRepositoryDB;
import core.RepoInter.DetailsRepository;
import entities.Article;
import entities.Detail;
import entities.Dette;
import entities.Paiement;

public class DetailRepositoryDB extends BaseRepositoryDB<Detail> implements DetailsRepository{

    public DetailRepositoryDB() {
        this.tableName = "detail";
    }

    @Override
    public void update(Detail objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Detail selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    @Override
    public void remove(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public Detail findby(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findby'");
    }

    @Override
    public List<Article> findByDette(Dette dette) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByDette'");
    }

    @Override
    protected String getInsertSQL() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInsertSQL'");
    }

    @Override
    protected String getSelectAllSQL() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSelectAllSQL'");
    }

    @Override
    protected void setInsertParameters(PreparedStatement pstmt, Detail objet) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setInsertParameters'");
    }

    @Override
    public Detail convertToObject(ResultSet rs) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToObject'");
    }

    @Override
    public List<Paiement> findDette(Dette dette) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findDette'");
    }
    
}
