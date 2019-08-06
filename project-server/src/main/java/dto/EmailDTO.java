package dto;

import entity.Email;
import entity.Message;
import entity.User;
import entity.enums.EmailType;

public class EmailDTO {
    private Integer id;
    private String receiver;
    private String type;
    private MessageDTO message;

    public EmailDTO() {
    }

    public EmailDTO(Integer id, String receiver, String type, MessageDTO message) {
        this.id = id;
        this.receiver = receiver;
        this.type = type;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getType() {
        return type;
    }

    public MessageDTO getMessage() {
        return message;
    }

    public static EmailDTO buildFromEntity(Email email) {
        return new Builder()
                .setId(email.getId())
                .setMessage(email.getMessage())
                .setReceiver(email.getReceiver())
                .setType(email.getType())
                .build();
    }

    private static class Builder {
        private Integer id;
        private String receiver;
        private String type;
        private MessageDTO message;

        public Builder() {
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setReceiver(User receiver) {
            this.receiver = receiver.getEmail();
            return this;
        }

        public Builder setType(EmailType type) {
            this.type = type.getName();
            return this;
        }

        public Builder setMessage(Message message) {
            this.message = MessageDTO.buildFromEntity(message);
            return this;
        }

        public EmailDTO build() {
            return new EmailDTO(id, receiver, type, message);
        }
    }

}
