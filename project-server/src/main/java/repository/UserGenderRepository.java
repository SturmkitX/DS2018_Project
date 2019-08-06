package repository;

import entity.enums.UserGender;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserGenderRepository extends CrudRepository<UserGender, Integer> {
    UserGender findByGender(String gender);
    List<UserGender> findAll();
}
