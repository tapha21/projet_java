package core.factory;

import repository.jpa.Articlejpa;
import repository.jpa.ClientJpa;
import repository.jpa.Detailsjpa;
import repository.jpa.Dettejpa;
import repository.jpa.Paiementjpa;
import repository.jpa.UserJpa;
import repository.list.ClientRepositoryList;
import repository.list.DetailRepositoryList;
import repository.list.DetteRepositoryList;
import repository.list.PaiementRepositoryList;
import repository.list.ArticleRepositoryList;
import repository.list.UserRepositoryList;
import repository.postgreImpl.ArticleRepositoryPostImpl;
import repository.postgreImpl.ClientRepositoryPostgreImpl;
import repository.postgreImpl.DetailRepositoryPostgreImpl;
import repository.postgreImpl.DetteRepositoryPostgreImpl;
import repository.postgreImpl.PaiementRepositoryPostImpl;
import repository.postgreImpl.RoleRepositoryPostImpl;
import repository.postgreImpl.UserRepositoryPostgreImpl;
import core.RepoInter.ArticleRepository;
import core.RepoInter.ClientRepository;
import core.RepoInter.DetailsRepository;
import core.RepoInter.DetteRepository;
import core.RepoInter.PaiementRepository;
import core.RepoInter.RoleRepository;
import core.RepoInter.UserRepository;

public class Factory {
    private static final String DATABASE_TYPE = "PostgreSQL"; // ou "JPA" / "List"

    private Factory() {}

    private static ClientRepository clientRepository = null;
    private static ArticleRepository articleRepository = null;
    private static DetteRepository dettesRepository = null;
    private static UserRepository userRepository = null;
    private static DetailsRepository detailsRepository = null;
    private static PaiementRepository paiementRepository = null;
    private static RoleRepository roleRepository = null;

    // ------------ ClientRepository --------
    public static ClientRepository getInstanceClientRepository() {
        if (clientRepository == null) {
            switch (DATABASE_TYPE) {
                case "PostgreSQL":
                    clientRepository = new ClientRepositoryPostgreImpl(new UserRepositoryPostgreImpl());
                    break;
                case "List":
                    clientRepository = new ClientRepositoryList();
                    break;
                default: // JPA par défaut
                    clientRepository = new ClientJpa();
                    break;
            }
        }
        return clientRepository;
    }

    // ------------ ArticleRepository --------
    public static ArticleRepository getInstanceArticleRepository() {
        if (articleRepository == null) {
            switch (DATABASE_TYPE) {
                case "PostgreSQL":
                    articleRepository = new ArticleRepositoryPostImpl();
                    break;
                case "List":
                    articleRepository = new ArticleRepositoryList();
                    break;
                default: // JPA par défaut
                    articleRepository = new Articlejpa();
                    break;
            }
        }
        return articleRepository;
    }

    // ------------ DetteRepository --------
    public static DetteRepository getInstanceDettesRepository() {
        if (dettesRepository == null) {
            switch (DATABASE_TYPE) {
                case "PostgreSQL":
                    dettesRepository = new DetteRepositoryPostgreImpl();
                    break;
                case "List":
                    dettesRepository = new DetteRepositoryList();
                    break;
                default: // JPA par défaut
                    dettesRepository = new Dettejpa();
                    break;
            }
        }
        return dettesRepository;
    }

    // ------------ UserRepository --------
    public static UserRepository getInstanceUserRepository() {
        if (userRepository == null) {
            switch (DATABASE_TYPE) {
                case "PostgreSQL":
                    userRepository = new UserRepositoryPostgreImpl();
                    break;
                case "List":
                    userRepository = new UserRepositoryList();
                    break;
                default:
                    userRepository = new UserJpa();
                    break;
            }
        }
        return userRepository;
    }

    // ------------ DetailsRepository --------
    public static DetailsRepository getInstanceDetails() {
        if (detailsRepository == null) {
            switch (DATABASE_TYPE) {
                case "PostgreSQL":
                    detailsRepository = new DetailRepositoryPostgreImpl();
                    break;
                case "List":
                    detailsRepository = new DetailRepositoryList();
                    break;
                default: // JPA par défaut
                    detailsRepository = new Detailsjpa();
                    break;
            }
        }
        return detailsRepository;
    }

    // ------------ PaiementRepository --------
    public static PaiementRepository getInstancePaiementRepository() {
        if (paiementRepository == null) {
            switch (DATABASE_TYPE) {
                case "PostgreSQL":
                    paiementRepository = new PaiementRepositoryPostImpl();
                    break;
                case "List":
                    paiementRepository = new PaiementRepositoryList();
                    break;
                default: // JPA par défaut
                    paiementRepository = new Paiementjpa();
                    break;
            }
        }
        return paiementRepository;
    }
    public static RoleRepository getInstanceRoleRepository() {
    if (roleRepository == null) {
        switch (DATABASE_TYPE) {
            case "PostgreSQL":
                roleRepository = new RoleRepositoryPostImpl();
                break;
            // case "List":
            //     roleRepository = new RoleRepositoryList();
            //     break;
            // default: // JPA par défaut
            //     roleRepository = new RoleJpa();
            //     break;
        }
    }
    return roleRepository;
}
}