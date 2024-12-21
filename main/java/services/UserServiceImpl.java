package services;
import java.util.List;

import core.RepoInter.Service;
import core.RepoInter.UserRepository;
import entities.Client;
import entities.User;
import core.factory.Factory;

// import repository.db.UserRepositoryDB;
import repository.list.UserRepositoryList;




public class UserServiceImpl implements Service <User> {
    private static int clientIdCounter = 1;
    private UserRepository userRepository =Factory.getInstanceUserRepository();
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

    public User Connection(String login, String password) {
       return userRepository.selectByLoginAndPassword(login, password);
    }

    public String findrole(String login) {
        return userRepository.getRoleByLogin(login);
    }
}
