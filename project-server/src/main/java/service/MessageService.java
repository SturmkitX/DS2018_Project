package service;

import dto.MessageDTO;
import entity.Message;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.MessageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public List<Message> findAllByReceiver(int id) {
        User user = userService.findById(id);
        return messageRepository.findAll().stream().filter(a -> a.getReceivers().contains(user))
                .collect(Collectors.toList());
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public Message buildEntityFromDTO(MessageDTO dto) {
        return Message.buildFromDTO(dto, userService);
    }
}
