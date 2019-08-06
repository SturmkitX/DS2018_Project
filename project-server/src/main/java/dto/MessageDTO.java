package dto;

import entity.Message;
import entity.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MessageDTO {
    private Integer id;
    private String subject;
    private String content;
    // the email
    private String sender;
    private String senderName;

    // separated by commas
    private List<Integer> receivers;

    public MessageDTO() {
    }

    public MessageDTO(Integer id, String subject, String content, String sender, String senderName, List<Integer> receivers) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.sender = sender;
        this.senderName = senderName;
        this.receivers = receivers;
    }

    public Integer getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public List<Integer> getReceivers() {
        return receivers;
    }

    public String getSenderName() {
        return senderName;
    }

    public static MessageDTO buildFromEntity(Message message) {
        return new Builder()
                .setId(message.getId())
                .setContent(message.getContent())
                .setReceivers(message.getReceivers())
                .setSender(message.getSender())
                .setSubject(message.getSubject())
                .build();
    }

    private static class Builder {
        private Integer id;
        private String subject;
        private String content;
        private String sender;
        private String senderName;
        private List<Integer> receivers;

        public Builder() {
        }

        public Builder setId(int id) {
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

        public Builder setSender(User sender) {
            this.sender = sender.getEmail();
            this.senderName = sender.getName();
            return this;
        }

        public Builder setReceivers(Set<User> receivers) {
            this.receivers = receivers.stream().map(a -> a.getId()).collect(Collectors.toList());
            return this;
        }

        public MessageDTO build() {
            return new MessageDTO(id, subject, content, sender, senderName, receivers);
        }
    }
}
