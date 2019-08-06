package dto;

import entity.Channel;
import entity.History;
import entity.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryDTO {
    private Integer id;

    // represents the unique 15 character id given to a channel
    private String channelId;
    private String channelName;
    private Integer ownerId;
    private String accessTime;

    public HistoryDTO() {
    }

    public HistoryDTO(Integer id, String channelId, String channelName, Integer ownerId, String accessTime) {
        this.id = id;
        this.channelId = channelId;
        this.channelName = channelName;
        this.ownerId = ownerId;
        this.accessTime = accessTime;
    }

    public Integer getId() {
        return id;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public String getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(String accessTime) {
        this.accessTime = accessTime;
    }

    public static HistoryDTO buildFromEntity(History history) {
        return new Builder()
                .setId(history.getId())
                .setChannel(history.getChannel())
                .setAccessTime(history.getAccessTime())
                .setOwnerId(history.getOwner())
                .build();
    }

    private static class Builder {
        private Integer id;
        private String channelId;
        private String channelName;
        private Integer ownerId;
        private String accessTime;

        public Builder() {
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setChannel(Channel channel) {
            this.channelId = channel.getDisplayId();
            this.channelName = channel.getName();
            return this;
        }

        public Builder setOwnerId(User owner) {
            this.ownerId = owner.getId();
            return this;
        }

        public Builder setAccessTime(Date accessTime) {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            this.accessTime = format.format(accessTime);
            return this;
        }

        public HistoryDTO build() {
            return new HistoryDTO(id, channelId, channelName, ownerId, accessTime);
        }
    }
}
