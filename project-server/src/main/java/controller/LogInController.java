package controller;

import dto.UserDTO;
import entity.Channel;
import entity.Email;
import entity.Message;
import entity.User;
import exception.EmailException;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class LogInController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailTypeService emailTypeService;

    @GetMapping("/login")
    public String displayHome(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("userInfo") == null) {
            return "User info undefined";
        }

        return "Session defined";
    }

    @PostMapping("/login")
    public UserDTO logUserIn(Authentication authentication) {
        // Spring Security will prohibit the user from reaching this point if the credentials are incorrect
        UserDetails details = (UserDetails) authentication.getPrincipal();
        User user = userService.findByEmail(details.getUsername());

        UserDTO dto = UserDTO.buildFromEntity(user);
        dto.disablePassword();

        return dto;
    }

    @PostMapping("/signup")
    public UserDTO signUp(@RequestBody UserDTO dto) throws EmailException, IOException {
        User user = userService.buildUserFromDTO(dto);
        if (userService.exists(user.getId())) {
            return null;
        }

        // the created user MUST be inactive first
        user.setActive(false);

        // verify email structure
        if (!Pattern.compile("\\S+@\\S+\\.\\S+").matcher(dto.getEmail()).matches()) {
            throw new EmailException();
        }

        // the password must be BCrypt
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userService.save(user);

        // create a stub, inactive channel
        RandomStringGenerator rsg = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                .build();
        Channel channel = new Channel(0, user, "", "", rsg.generate(15), null, false);
        channelService.save(channel);

        // create confirmation mail
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResource("/confirmation-email.html").openStream()));
        String mailContent = reader.readLine().replace("{{name}}", user.getName());
        mailContent = mailContent.replace("{{id}}", "" + user.getId());

        Message sendMsg = new Message();
        sendMsg.setSender(userService.findById(4));
        sendMsg.setContent(mailContent);
        sendMsg.setSubject("Account activation");

        Set<User> users = new HashSet<>();
        users.add(user);
        sendMsg.setReceivers(users);
        sendMsg = messageService.save(sendMsg);

        Email sendEmail = new Email();
        sendEmail.setMessage(sendMsg);
        sendEmail.setReceiver(user);

        sendEmail.setType(emailTypeService.findByName("Activation"));
        emailService.save(sendEmail);
        return UserDTO.buildFromEntity(user);
    }
}
