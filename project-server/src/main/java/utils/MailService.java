package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailService {
    final String username;
    final String password;
    final Properties props;

    /**
     * Builds a mail service class, used for sending e-mails.
     * The credentials provided should be the ones needed to
     * autenthicate to the SMTP server (GMail by default).
     *
     * @param username username to log in to the smtp server
     * @param password password to log in to the smtp server
     */
    public MailService(String username, String password) {
        this.username = username;
        this.password = password;

        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }


    /**
     * Sends an email with the subject and content specified, to
     * the address specified.
     *
     * @param to address to send email to
     * @param subject subject of the email
     * @param content content of the email
     */
    public void sendMail(String to, String subject, Object content) throws MessagingException {
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(content.toString(), "UTF-8", "html");

        Transport.send(message);
        System.out.println("Mail sent.");
    }


}