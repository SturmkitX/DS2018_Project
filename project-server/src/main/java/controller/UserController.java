package controller;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import dto.UserDTO;
import entity.Channel;
import entity.User;
import exception.EmailException;
import exception.UserInfoException;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import service.ChannelService;
import service.UserService;

@RestController
@RequestMapping("/resource/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    @GetMapping("")
    public List<UserDTO> getAllUsers() {
        List<User> users =  userService.findAll();
        List<UserDTO> dtos = users.stream().map(a -> UserDTO.buildFromEntity(a)).collect(Collectors.toList());

        return dtos;
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable(required = true) int id) {
        User a = userService.findById(id);
        return UserDTO.buildFromEntity(a);
    }

    @PostMapping("")
    public UserDTO postUser(@RequestBody UserDTO dto) {
        User user = userService.buildUserFromDTO(dto);
        if (userService.exists(user.getId())) {
            return null;
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
        return UserDTO.buildFromEntity(user);
    }

    @PutMapping("")
    public UserDTO updateUser(@RequestBody UserDTO dto) throws UserInfoException {
        User user = userService.buildUserFromDTO(dto);
        String currentName = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentName = authentication.getName();
        }

        System.out.println(currentName);

        if (currentName == null) {
            return null;
        }
        if (!userService.exists(user.getId())) {
            return null;
        }

        User auth = userService.findByEmail(currentName);
        System.out.println(auth);
        if (auth == null || (auth.getId() != user.getId() && !auth.getRole().getRole().equals("Administrator"))) {
            throw new UserInfoException();
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        if (user.getPassword().length() > 0) {
            user.setPassword(encoder.encode(user.getPassword()));
        } else {
            user.setPassword(auth.getPassword());
        }

        userService.save(user);
        return UserDTO.buildFromEntity(user);
    }

    @GetMapping("/activate/{id}")
    public UserDTO activateUser(@PathVariable int id) {
        User user = userService.findById(id);
        if (user == null) {
            return null;
        }

        user.setActive(true);
        userService.save(user);

        return UserDTO.buildFromEntity(user);
    }
}