package core.RepoInter;

import core.config.Repository;
import entities.Dette;
import entities.Role;

public interface RoleRepository extends Repository<Role> {
        void save(Role objet);
}
