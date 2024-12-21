package com.repository.jpa;

import javax.persistence.EntityManager;

import com.core.config.repoImpl.RepositoryJpaImpl;
import com.entities.Client;
import com.entities.Commande;

public class CommandeJpa  extends RepositoryJpaImpl<Commande>{
    private EntityManager entityManager;

    public CommandeJpa() {
        super(Commande.class);
        this.entityManager = entityManager;
    }
}
