package core.factory.facto;

import repository.list.*;

public class ListRepository implements RepoInt<Object> {
    private String type;

    public ListRepository(String type) {
        this.type = type;
    }

    @Override
    public Object getInstance() {
        switch (type) {
            case "Client":
                return new ClientRepositoryList();
            case "User":
                return new UserRepositoryList();
            case "Dette":
                return new DetteRepositoryList();
            case "Article":
                return new ArticleRepositoryList();
            case "Details":
                return new DetailRepositoryList();
            case "Paiement":
                return new PaiementRepositoryList();
            default:
                throw new UnsupportedOperationException("Type de repository List non supporté");
        }
    }
}
