package repository;

import entity.Channel;
import entity.History;
import entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HistoryRepository extends CrudRepository<History, Integer> {
    List<History> findTop15ByOwner(User owner);
    History findById(int id);
    List<History> findByChannel(Channel channel);
}
