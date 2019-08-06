package entity.enums;

import javax.persistence.*;

@Entity
@Table(name = "genders")
public class UserGender {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", unique = true, length = 15)
    private String gender;

    public UserGender() {
    }

    public UserGender(int id, String gender) {
        this.id = id;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
