package repository;

import entity.enums.ChannelCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChannelCategoryRepository extends CrudRepository<ChannelCategory, Integer> {
    ChannelCategory findByCategory(String category);
    List<ChannelCategory> findAll();
}
