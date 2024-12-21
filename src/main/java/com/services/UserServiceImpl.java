package com.services;
import java.util.List;

import com.core.RepoInter.Service;
import com.core.RepoInter.UserRepository;
import com.entities.Client;
import com.entities.User;
import com.core.factory.Factory;

// import com.repository.db.UserRepositoryDB;




public class UserServiceImpl implements Service <User> {
    private static int clientIdCounter = 1;
    private UserRepository userRepository =Factory.getInstanceUserRepository();
    // private UserRepositoryDB userRepository;
 
    public UserServiceImpl(UserRepository userRepository2){
        this.userRepository=userRepository2;
    }

    public void create(User user){
        user.setId(clientIdCounter++);
        userRepository.insert(user);
    }

    public List<User> findAll(){
        return userRepository.selectAll();
    }

    @Override
    public void save(User objet) {
        objet.setId(clientIdCounter++);
        userRepository.insert(objet);
    }

    @Override
    public List<User> find() {
        return userRepository.selectAll();
    }
    @Override
    public void update(User objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public void incrementUserIds(List<User> users) {
        int idCounter = 1; // Démarre à 1, ou tout autre valeur de départ

        for (User user : users) {
            user.setId(idCounter); // Incrémente et assigne l'ID
            idCounter++; // Augmente l'ID pour l'utilisateur suivant
        }
    }
    public void affichebyrole(String role){
        userRepository.selectByRole(role);
    }

    public User findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);

    }
    
}
