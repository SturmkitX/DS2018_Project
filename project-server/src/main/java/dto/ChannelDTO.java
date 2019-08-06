package dto;

import entity.Channel;
import entity.User;
import entity.enums.ChannelCategory;

public class ChannelDTO {
    private Integer id;
    private Integer ownerId;
    private String ownerName;
    private String name;
    private String description;
    private String displayId;
    private String category;
    private Boolean active;

    public ChannelDTO() {
    }

    public ChannelDTO(Integer id, Integer ownerId, String ownerName, String name, String description, String displayId, String category, Boolean active) {
        this.id = id;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.name = name;
        this.description = description;
        this.displayId = displayId;
        this.category = category;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
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

    public String getCategory() {
        return category;
    }

    public Boolean getActive() {
        return active;
    }

    public static ChannelDTO buildFromEntity(Channel channel) {
        return new Builder()
                .setId(channel.getId())
                .setActive(channel.isActive())
                .setCategory(channel.getCategory())
                .setDescription(channel.getDescription())
                .setDisplayId(channel.getDisplayId())
                .setName(channel.getName())
                .setOwner(channel.getOwner())
                .build();
    }

    private static class Builder {
        private Integer id;
        private Integer ownerId;
        private String ownerName;
        private String name;
        private String description;
        private String displayId;
        private String category;
        private Boolean active;
        public Builder() {
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setOwner(User owner) {
            this.ownerId = owner.getId();
            this.ownerName = owner.getName();
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

        public Builder setCategory(ChannelCategory category) {
            this.category = category != null ? category.getCategory() : "<Unspecified>";
            return this;
        }

        public Builder setActive(Boolean active) {
            this.active = active;
            return this;
        }

        public ChannelDTO build() {
            return new ChannelDTO(id, ownerId, ownerName, name, description, displayId, category, active);
        }
    }
}
