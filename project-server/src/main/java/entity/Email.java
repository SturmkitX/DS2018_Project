package entity;

import entity.enums.EmailType;

import javax.persistence.*;

@Entity
@Table(name = "emails")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(targetEntity = User.class)
    private User receiver;

    @JoinColumn(name = "email_type", nullable = false)
    @ManyToOne(targetEntity = EmailType.class)
    private EmailType type;

    @JoinColumn(name = "message_id")
    @ManyToOne(targetEntity = Message.class)
    private Message message;

    public Email() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public EmailType getType() {
        return type;
    }

    public void setType(EmailType type) {
        this.type = type;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
