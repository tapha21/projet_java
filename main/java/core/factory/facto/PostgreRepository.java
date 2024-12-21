package core.factory.facto;

import core.RepoInter.UserRepository;
import repository.postgreImpl.*;

public class PostgreRepository implements RepoInt<Object> {
    private String type;

    public PostgreRepository(String type) {
        this.type = type;
    }

    @Override
    public Object getInstance() {
        switch (type) {
            case "Client":
                return new ClientRepositoryPostgreImpl(new UserRepositoryPostgreImpl());
            case "User":
                return new UserRepositoryPostgreImpl();
            case "Dette":
                return new DetteRepositoryPostgreImpl();
            case "Article":
                return new ArticleRepositoryPostImpl();
            case "Details":
                return new DetailRepositoryPostgreImpl();
            case "Paiement":
                return new PaiementRepositoryPostImpl();
            default:
                throw new UnsupportedOperationException("Type de repository PostgreSQL non supporté : " + type);
        }
    }
}
