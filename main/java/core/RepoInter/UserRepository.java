package core.RepoInter;


import core.config.Repository;
import entities.User;
import java.util.List;
public interface UserRepository extends Repository<User> {
    public List<User> selectByRole(String role);
    public List<User> selectActive(boolean active);
    User selectByID(int id );
    User selectByLoginAndPassword(String Login, String password);
    List<User> search(String tel);
    String getRoleByLogin(String login);
}
