package repository;

import entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findAll();
    User findById(int id);
    User findByEmail(String email);
}
