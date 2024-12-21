package com.core.factory;

import com.repository.jpa.Articlejpa;
import com.repository.jpa.ClientJpa;
import com.repository.jpa.Detailsjpa;
import com.repository.jpa.Dettejpa;
import com.repository.jpa.Paiementjpa;
import com.repository.jpa.UserJpa;
import com.core.RepoInter.ArticleRepository;
import com.core.RepoInter.ClientRepository;
import com.core.RepoInter.DetailsRepository;
import com.core.RepoInter.DetteRepository;
import com.core.RepoInter.PaiementRepository;
import com.core.RepoInter.UserRepository;

public class Factory {
    private Factory() {
        // Constructeur privé pour empêcher l'instanciation
    }

    private static ClientRepository clientRepository = null;
    private static ArticleRepository articleRepository = null;
    private static DetteRepository dettesRepository = null;
    private static UserRepository userRepository = null;
    private static DetailsRepository detailsRepository = null;
    private static PaiementRepository paiementRepository = null;


    // ------------ Base de données --------
    public static ClientRepository getInstanceClientRepository() {
        if (clientRepository == null) {
            clientRepository = new ClientJpa();
        }
        return clientRepository;
    }

    public static UserRepository getInstanceUserRepository() {
        if (userRepository == null) {
            userRepository = new UserJpa();
        }
        return userRepository;
    }

    public static DetteRepository getInstanceDettesRepository() {
        if (dettesRepository == null) {
            dettesRepository = new Dettejpa();
        }
        return dettesRepository;
    }

    public static ArticleRepository getInstanceArticleRepository() {
        if (articleRepository == null) {
            articleRepository = new Articlejpa();
        }
        return articleRepository;
    }

    public static DetailsRepository getInstanceDetails() {
        if (detailsRepository == null) {
            detailsRepository = new Detailsjpa();
        }
        return detailsRepository;
    }

    public static PaiementRepository getInstancePaiment() {
        if (paiementRepository == null) {
            paiementRepository = new Paiementjpa();
        }
        return paiementRepository;
    }
}
