package services;

import core.RepoInter.DetailsRepository;
import core.RepoInter.UserRepository;
import core.factory.Factory;

public class DetailsServiceImpl {
    
    private DetailsRepository detailsRepository =Factory.getInstanceDetails();
    public DetailsServiceImpl(DetailsRepository detailsRepositoro){
        this.detailsRepository=detailsRepositoro;
    }

}
