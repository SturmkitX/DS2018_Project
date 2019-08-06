package repository;

import entity.Channel;
import entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChannelRepository extends CrudRepository<Channel, Integer> {
    Channel findById(int id);
    Channel findByDisplayId(String displayId);
    List<Channel> findAll();
    List<Channel> findByActive(boolean active);
    Channel findByOwner(User owner);
}
