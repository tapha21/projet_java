package com.services;

import com.core.RepoInter.DetailsRepository;
import com.core.RepoInter.UserRepository;
import com.core.factory.Factory;

public class DetailsServiceImpl {
    
    private DetailsRepository detailsRepository =Factory.getInstanceDetails();
    public DetailsServiceImpl(DetailsRepository detailsRepositoro){
        this.detailsRepository=detailsRepositoro;
    }

}
