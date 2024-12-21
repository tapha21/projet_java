package com.core.RepoInter;


import com.core.config.Repository;
import com.entities.User;
import java.util.List;
public interface UserRepository extends Repository<User> {
    public List<User> selectByRole(String role);
    public List<User> selectActive(boolean active);
    User selectByID(int id );
    User selectByLogin(String login );
    List<User> search(String tel);
    User findByLoginAndPassword(String login, String password);
}
