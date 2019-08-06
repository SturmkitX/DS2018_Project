package controller;

import dto.EmailDTO;
import entity.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.EmailService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resource/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("")
    public List<EmailDTO> getAllEmails() {
        // an email is present in the database ONLY IF it hasn't been sent yet
        List<Email> emails = emailService.findAll();
        return emails.stream().map(a -> EmailDTO.buildFromEntity(a)).collect(Collectors.toList());
    }

    // should be a delete mapping
    // but, according to the standard, DELETE does not accept a body
    @PostMapping("")
    public void deleteAllEmails(@RequestBody List<Integer> deleteIds) {
        List<Email> emails = deleteIds.stream().map(a -> emailService.findById(a)).collect(Collectors.toList());
        emailService.delete(emails);
    }
}
