package unmsm.BibliotecaCentralPedroZulen.service;

import unmsm.BibliotecaCentralPedroZulen.model.Role;
import unmsm.BibliotecaCentralPedroZulen.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}
