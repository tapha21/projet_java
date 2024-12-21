package com.core.factory.facto;

import com.repository.jpa.*;

public class JpaRepository<T> implements RepoInt<Object> {
    private String type;
    

    public JpaRepository(String type) {
        this.type = type;
    }
    
    @Override
    public Object getInstance() {
        switch (type) {
            case "Client":
                return new ClientJpa();
            case "User":
                return new UserJpa();
            case "Dette":
                return new Dettejpa();
            case "Article":
                return new Articlejpa();
            case "Details":
                return new Detailsjpa();
            case "Paiement":
                return new Paiementjpa();
            default:
                throw new UnsupportedOperationException("Type de repository JPA non supporté : " + type);
        }
    }
}
