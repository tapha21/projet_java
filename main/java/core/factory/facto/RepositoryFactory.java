package core.factory.facto;

import core.RepoInter.ClientRepository;
import core.RepoInter.UserRepository;
import core.RepoInter.DetteRepository;
import core.RepoInter.PaiementRepository;
import core.RepoInter.ArticleRepository;
import core.RepoInter.DetailsRepository;
import java.util.HashMap;
import java.util.Map;

public class RepositoryFactory {
    private static final Map<Class<?>, String> REPOSITORY_TYPES = new HashMap<>();
    private static final String REPOSITORY_TYPE = "Sama";

    static {
        REPOSITORY_TYPES.put(ClientRepository.class, "Client");
        REPOSITORY_TYPES.put(UserRepository.class, "User");
        REPOSITORY_TYPES.put(DetteRepository.class, "Dette");
        REPOSITORY_TYPES.put(ArticleRepository.class, "Article");
        REPOSITORY_TYPES.put(DetailsRepository.class, "Details");
        REPOSITORY_TYPES.put(PaiementRepository.class, "Paiement");
    }

    public static <T> T getRepository(Class<T> clazz) {
        String type = REPOSITORY_TYPES.get(clazz);
        if (type == null) {
            throw new UnsupportedOperationException("Type de repository non supporté : " + clazz.getSimpleName());
        }
        RepoInt<T> repository = (RepoInt<T>) getRepositoryInstance(type);
        return repository.getInstance();
    }

    private static <T> RepoInt<T> getRepositoryInstance(String type) {
        if (REPOSITORY_TYPE.equals("Sama")) {
            // return (RepoInt<T>) new JpaRepository(type);
            return (RepoInt<T>) new PostgreRepository(type);
            // return (RepoInt<T>) new ListRepository(type);

        } else {
            // return (RepoInt<T>) new JpaRepository(type);
            // return (RepoInt<T>) new PostgreRepository(type);
            return (RepoInt<T>) new ListRepository(type);

        }
    }
}
