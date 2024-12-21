package services;

import java.util.List;

import core.RepoInter.RoleRepository;
import core.RepoInter.Service;
import entities.Paiement;
import entities.Role;

public class RoleServiceImpl implements Service <Role>{
 private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(Role objet) {
        roleRepository.save(objet);
    }

    @Override
    public List<Role> find() {
        return roleRepository.selectAll();
    }

    @Override
    public void update(Role objet) {
        roleRepository.update(objet);
    }
}
