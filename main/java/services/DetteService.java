package services;

import java.util.List;

import entities.Client;
import entities.Dette;


public interface DetteService {
    void createDette(Dette dette);
    List<Dette> findAll();
    Client search(String tel);
}
