package entity;

import dto.UserDTO;
import entity.enums.UserGender;
import entity.enums.UserRole;
import service.UserGenderService;
import service.UserRoleService;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @JoinColumn(name = "role_id", nullable = false)
    @ManyToOne(targetEntity = UserRole.class)
    private UserRole role;

    @JoinColumn(name = "gender_id")
    @ManyToOne(targetEntity = UserGender.class)
    private UserGender gender;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "enable_emails", nullable = false)
    private boolean enableEmails;

    @ManyToMany(targetEntity = Message.class)
    @JoinTable(name = "messages_users",
            joinColumns = {@JoinColumn(name = "receiver_id")},
            inverseJoinColumns = {@JoinColumn(name = "message_id")}
            )
    private Set<Message> receivedMessages;

    public User() {
    }

    public User(int id, String name, String email, String password, UserRole role, UserGender gender, boolean active, boolean enableEmails, Set<Message> receivedMessages) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.active = active;
        this.enableEmails = enableEmails;
        this.receivedMessages = receivedMessages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserGender getGender() {
        return gender;
    }

    public void setGender(UserGender gender) {
        this.gender = gender;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isEnableEmails() {
        return enableEmails;
    }

    public void setEnableEmails(boolean enableEmails) {
        this.enableEmails = enableEmails;
    }

    public Set<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(Set<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static User buildFromDTO(UserDTO dto, UserRoleService roleService, UserGenderService genderService) {
        return new Builder(roleService, genderService)
                .setId(dto.getId())
                .setActive(dto.getActive())
                .setEmail(dto.getEmail())
                .setGender(dto.getGender())
                .setName(dto.getName())
                .setPassword(dto.getPassword())
                .setRole(dto.getRole())
                .setEnableEmails(dto.getEnableEmails())
                .build();
    }

    private static class Builder {
        private int id;
        private String name;
        private String email;
        private String password;
        private UserRole role;
        private UserGender gender;
        private boolean active;
        private boolean enableEmails;

        private UserRoleService roleService;
        private UserGenderService genderService;

        public Builder(UserRoleService roleService, UserGenderService genderService) {
            this.roleService = roleService;
            this.genderService = genderService;
        }

        public Builder setId(Integer id) {
            if (id != null) {
                this.id = id;
            }
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setRole(String role) {
            this.role = roleService.findByRole(role);
            return this;
        }

        public Builder setGender(String gender) {
            this.gender = genderService.findByGender(gender);
            return this;
        }

        public Builder setActive(Boolean active) {
            this.active = active;
            return this;
        }

        public Builder setEnableEmails(Boolean enableEmails) {
            this.enableEmails = enableEmails;
            return this;
        }

        public User build() {
            return new User(id, name, email, password, role, gender, active, enableEmails, null);
        }

    }
}
