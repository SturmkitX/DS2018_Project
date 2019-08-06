package controller;

import dto.MessageDTO;
import entity.Email;
import entity.Message;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.EmailService;
import service.EmailTypeService;
import service.MessageService;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resource/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailTypeService emailTypeService;

    @GetMapping("/{id}")
    public List<MessageDTO> getAll(@PathVariable int id) {
        List<Message> messages = messageService.findAllByReceiver(id);
        return messages.stream().map(MessageDTO::buildFromEntity)
                .collect(Collectors.toList());
    }

    @PostMapping("")
    public MessageDTO saveMessage(@RequestBody MessageDTO dto) throws IOException {
        Message message = messageService.buildEntityFromDTO(dto);
        message = messageService.save(message);

        Set<User> emailReceivers = message.getReceivers().stream().filter(User::isEnableEmails)
                .collect(Collectors.toSet());

        if (emailReceivers.isEmpty()) {
            return MessageDTO.buildFromEntity(message);
        }

        final Message emailMsg = new Message();
        emailMsg.setSender(message.getSender());
        emailMsg.setSubject("Email notification: " + message.getSubject());

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/conversation-email.html")
        ));
        String emailContent = reader.readLine().replace("{{sender}}", message.getSender().getName());
        emailContent = emailContent.replace("{{message-content}}", message.getContent());
        emailMsg.setContent(emailContent);


        emailMsg.setReceivers(emailReceivers);

        messageService.save(emailMsg);


        // send emails to the users, if necessary
        for (User user : emailReceivers) {
            Email email = new Email();
            email.setType(emailTypeService.findByName("Conversation"));
            email.setMessage(emailMsg);
            email.setReceiver(user);
            emailService.save(email);
        };

        return MessageDTO.buildFromEntity(message);
    }
}
