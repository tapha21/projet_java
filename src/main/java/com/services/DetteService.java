package com.services;

import java.util.List;

import com.entities.Client;
import com.entities.Dette;


public interface DetteService {
    void createDette(Dette dette);
    List<Dette> findAll();
    Client search(String tel);
    
}
