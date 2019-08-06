package entity;

import dto.MessageDTO;
import service.UserService;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "subject", length = 100)
    private String subject;

    @Column(name = "content", nullable = false)
    private String content;

    @JoinColumn(name = "sender_id", nullable = false)
    @ManyToOne(targetEntity = User.class)
    private User sender;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "messages_users",
            joinColumns = { @JoinColumn(name = "message_id") },
            inverseJoinColumns = { @JoinColumn(name = "receiver_id") }
    )
    private Set<User> receivers;

    public Message() {
    }

    public Message(int id, String subject, String content, User sender, Set<User> receivers) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.sender = sender;
        this.receivers = receivers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Set<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(Set<User> receivers) {
        this.receivers = receivers;
    }

    public static Message buildFromDTO(MessageDTO dto, UserService userService) {
        return new Builder(userService)
                .setId(dto.getId())
                .setSubject(dto.getSubject())
                .setContent(dto.getContent())
                .setSender(dto.getSender())
                .setReceivers(dto.getReceivers())
                .build();
    }

    private static class Builder {
        private int id;
        private String subject;
        private String content;
        private User sender;
        private Set<User> receivers;

        private UserService userService;

        public Builder(UserService userService) {
            this.userService = userService;
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setSender(String sender) {
            this.sender = userService.findByEmail(sender);
            return this;
        }

        public Builder setReceivers(List<Integer> receivers) {
            this.receivers = receivers.stream().map(a -> userService.findById(a))
                    .collect(Collectors.toSet());
            return this;
        }

        public Message build() {
            return new Message(id, subject, content, sender, receivers);
        }
    }
}
