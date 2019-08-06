package service;

import dto.CommentDTO;
import entity.Channel;
import entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public List<Comment> findByChannel(Channel channel) {
        return commentRepository.findByChannel(channel);
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment buildEntityFromDTO(CommentDTO dto) {
        return Comment.buildFromDTO(dto, userService, channelService);
    }
}
