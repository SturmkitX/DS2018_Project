package repository;

import entity.enums.EmailType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmailTypeRepository extends CrudRepository<EmailType, Integer> {
    List<EmailType> findAll();
    EmailType findByName(String name);
}
