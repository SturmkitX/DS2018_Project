package controller;

import dto.CommentDTO;
import entity.Channel;
import entity.Comment;
import exception.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import service.ChannelService;
import service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resource/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ChannelService channelService;

    @GetMapping("/{channelId}")
    public List<CommentDTO> getAllComments(@PathVariable String channelId) {
        Channel channel = channelService.findByDisplayId(channelId);
        List<Comment> comments =  commentService.findByChannel(channel);
        return comments.stream().map(a -> CommentDTO.buildFromEntity(a)).collect(Collectors.toList());
    }

    @PostMapping("")
    public CommentDTO postComment(@RequestBody CommentDTO dto) {
        Comment comment = commentService.buildEntityFromDTO(dto);
        Comment saved = commentService.save(comment);
        return CommentDTO.buildFromEntity(saved);
    }
}
