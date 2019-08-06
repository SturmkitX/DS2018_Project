package entity;

import dto.ChannelDTO;
import entity.enums.ChannelCategory;
import service.ChannelCategoryService;
import service.UserService;

import javax.persistence.*;

@Entity
@Table(name = "channels")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne(targetEntity = User.class)
    private User owner;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "display_id", length = 20, unique = true, nullable = false)
    private String displayId;

    @JoinColumn(name = "category_id")
    @ManyToOne(targetEntity = ChannelCategory.class)
    private ChannelCategory category;

    @Column(name = "active", nullable = false)
    private boolean active;

//    @ManyToMany(targetEntity = Comment.class)
//    @JoinTable(name = "channel_comments",
//            joinColumns = {@JoinColumn(name = "channel_id")},
//            inverseJoinColumns = {@JoinColumn(name = "user_id")}
//    )
//    private Set<Comment> comments;

    public Channel() {
    }

    public Channel(int id, User owner, String name, String description, String displayId, ChannelCategory category, boolean active) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.displayId = displayId;
        this.category = category;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayId() {
        return displayId;
    }

    public ChannelCategory getCategory() {
        return category;
    }

    public boolean isActive() {
        return active;
    }

    public static Channel buildFromDTO(ChannelDTO dto, ChannelCategoryService categoryService, UserService userService) {
        return new Builder(categoryService, userService)
                .setId(dto.getId())
                .setActive(dto.getActive())
                .setCategory(dto.getCategory())
                .setDescription(dto.getDescription())
                .setDisplayId(dto.getDisplayId())
                .setName(dto.getName())
                .setOwner(dto.getOwnerId())
                .build();
    }

    private static class Builder {
        private int id;
        private User owner;
        private String name;
        private String description;
        private String displayId;
        private ChannelCategory category;
        private boolean active;

        private ChannelCategoryService categoryService;
        private UserService userService;

        public Builder(ChannelCategoryService categoryService, UserService userService) {
            this.categoryService = categoryService;
            this.userService = userService;
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setOwner(Integer ownerId) {
            this.owner = userService.findById(ownerId);
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDisplayId(String displayId) {
            this.displayId = displayId;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = categoryService.findByCategory(category);
            return this;
        }

        public Builder setActive(Boolean active) {
            this.active = active;
            return this;
        }

        public Channel build() {
            return new Channel(id, owner, name, description, displayId, category, active);
        }
    }
}
