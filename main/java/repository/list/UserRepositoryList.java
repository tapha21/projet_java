package repository.list;

import java.util.List;
import java.util.stream.Collectors;

import core.config.repoImpl.RepositoryImpl;
import core.RepoInter.UserRepository;
import entities.User;

public class UserRepositoryList extends RepositoryImpl<User> implements UserRepository {

    @Override
    public List<User> selectByRole(String role) {
        return list.stream()
                   .filter(user -> user.getRole().equals(role))
                   .collect(Collectors.toList());
    }

    @Override
    public void update(User updatedUser) {
        list.stream()
            .filter(user -> user.getId() == updatedUser.getId())
            .findFirst()
            .ifPresent(user -> {
                user.setLogin(updatedUser.getLogin());
                user.setRole(updatedUser.getRole());
                user.setNom(updatedUser.getNom());
                user.setPrenom(updatedUser.getPrenom());
            });
    }

    @Override
    public List<User> search(String nom) {
        return list.stream()
                   .filter(user -> user.getNom().equals(nom))
                   .collect(Collectors.toList());
    }

    @Override
    public User selectByID(int id) {
        return list.stream()
                   .filter(user -> user.getId() == id)
                   .findFirst()
                   .orElse(null);
    }

    @Override
    public User selectByLoginAndPassword(String Login, String password) {
        return list.stream()
                   .filter(user -> user.getLogin().equals(Login))
                   .filter(user -> user.getPassword().equals(password))
                   .findFirst()
                   .orElse(null);
    }

    @Override
    public List<User> selectActive(boolean active) {
        throw new UnsupportedOperationException("Unimplemented method 'selectActive'");
    }

    @Override
    public User selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

   

    @Override
    public User findby(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findby'");
    }

    @Override
    public void remove(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public String getRoleByLogin(String login) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRoleByLogin'");
    }

    // @Override
    // public List<User> selectActive(boolean active) {
    //     return list.stream()
    //                .filter(user -> user.isEtat() == active)
    //                .collect(Collectors.toList());
    // }
}
