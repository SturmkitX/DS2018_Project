package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.EmailDTO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.mail.MessagingException;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.TimerTask;

public class MailSender extends TimerTask {

    private MailService mailService;

    public MailSender() {
        mailService = new MailService("bogdan.rogoz.dev@gmail.com", "devaccount");
    }

    @Override
    public void run() {
        HttpUriRequest request = new HttpGet("http://localhost:8080/resource/email");
        ObjectMapper mapper = new ObjectMapper();
        List emails = null;
        try {
            String token = "email-sender@bogdan.ro:testpass";
            request.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(token.getBytes()));
            HttpResponse response = HttpClientBuilder.create().build().execute( request );
            emails = mapper.readValue(response.getEntity().getContent(), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (emails == null) {
            return;
        }

        List<Integer> toDelete = new ArrayList<>();
        for (Object o : emails) {
            EmailDTO dto = mapper.convertValue(o, EmailDTO.class);
            // should be refactored, so each email type contains a different template
            try {
                mailService.sendMail(dto.getReceiver(), dto.getMessage().getSubject(), dto.getMessage().getContent());
                toDelete.add(dto.getId());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        // delete the successfully sent emails

        try {
            request = new HttpPost("http://localhost:8080/resource/email");
            String token = "email-sender@bogdan.ro:testpass";
            request.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(token.getBytes()));

            HttpEntity entity = new StringEntity(mapper.writeValueAsString(toDelete));
            request.setHeader("Content-Type", "application/json");
            ((HttpPost) request).setEntity(entity);
            HttpResponse response = HttpClientBuilder.create().build().execute( request );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MailSender sender = new MailSender();
        sender.run();
    }
}
