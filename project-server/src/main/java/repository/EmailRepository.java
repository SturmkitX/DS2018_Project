package repository;

import entity.Email;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmailRepository extends CrudRepository<Email, Integer> {
    List<Email> findAll();
}
