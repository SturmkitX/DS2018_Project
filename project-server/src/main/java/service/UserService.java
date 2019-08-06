package service;

import dto.UserDTO;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleService roleService;

    @Autowired
    private UserGenderService genderService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        // check everything that can't be verified by the database
        if (user.getId() < 0) {
            return null;
        }

        if (!Pattern.compile("\\S+@\\S+\\.\\S+").matcher(user.getEmail()).matches()) {
            return null;
        }

        userRepository.save(user);
        return user;
    }

    public User findByEmail(String name) {
        return userRepository.findByEmail(name);
    }

    public boolean exists(int id) {
        return userRepository.existsById(id);
    }

    public User buildUserFromDTO(UserDTO dto) {
        return User.buildFromDTO(dto, roleService, genderService);
    }
}
