package entity.enums;

import javax.persistence.*;

@Entity
@Table(name = "channel_categories")
public class ChannelCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", unique = true, length = 40)
    private String category;

    public ChannelCategory() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
