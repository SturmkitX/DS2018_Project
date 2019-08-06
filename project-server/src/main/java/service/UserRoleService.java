package service;

import entity.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRoleRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserRoleService {
    // the database may change very rarely
    // so we will employ a cache
    private static Map<String, UserRole> roles = new HashMap<>();

    @Autowired
    private UserRoleRepository roleRepository;

    public UserRole findByRole(String role) {
        if (roles.get(role) == null) {
            roles.put(role, roleRepository.findByRole(role));
        }
        return roles.get(role);
    }

}
