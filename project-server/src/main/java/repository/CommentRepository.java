package repository;

import entity.Channel;
import entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    List<Comment> findAll();
    List<Comment> findByChannel(Channel channelId);
}
