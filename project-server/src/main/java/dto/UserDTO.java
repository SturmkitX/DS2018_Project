package dto;

import entity.User;
import entity.enums.UserGender;
import entity.enums.UserRole;

public class UserDTO {

    private Integer id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String gender;
    private Boolean active;
    private Boolean enableEmails;

    public UserDTO() {
    }

    public UserDTO(Integer id, String name, String email, String password, String role, String gender, Boolean active, Boolean enableEmails) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.active = active;
        this.enableEmails = enableEmails;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getGender() {
        return gender;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getEnableEmails() {
        return enableEmails;
    }

    public void disablePassword() {
        this.password = null;
    }

    public static UserDTO buildFromEntity(User user) {
        return new Builder()
                .setId(user.getId())
                .setActive(user.isActive())
                .setEmail(user.getEmail())
                .setName(user.getName())
                .setPassword(user.getPassword())
                .setRole(user.getRole())
                .setGender(user.getGender())
                .setEnableEmails(user.isEnableEmails())
                .build();
    }

    private static class Builder {
        private Integer id;
        private String name;
        private String email;
        private String password;
        private String role;
        private String gender;
        private Boolean active;
        private Boolean enableEmails;
        public Builder() {
        }

        public Builder setId(Integer id) {
            this.id = id;
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

        public Builder setRole(UserRole role) {
            this.role = role.getRole();
            return this;
        }

        public Builder setGender(UserGender gender) {
            this.gender = (gender != null) ? gender.getGender() : "<Unspecified>";
            return this;
        }

        public Builder setActive(boolean active) {
            this.active = active;
            return this;
        }

        public Builder setEnableEmails(boolean enableEmails) {
            this.enableEmails = enableEmails;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(id, name, email, password, role, gender, active, enableEmails);
        }
    }
}
