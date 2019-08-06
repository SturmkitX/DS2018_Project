package entity;

import dto.HistoryDTO;
import service.ChannelService;
import service.UserService;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "history_entries")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(targetEntity = User.class)
    private User owner;

    @JoinColumn(name = "channel_id", nullable = false)
    @ManyToOne(targetEntity = Channel.class)
    private Channel channel;

    @Column(name = "access_time")
    private Date accessTime;

    public History() {
    }

    public History(int id, User owner, Channel channel, Date accessTime) {
        this.id = id;
        this.owner = owner;
        this.channel = channel;
        this.accessTime = accessTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public static History buildFromDTO(HistoryDTO dto, UserService userService, ChannelService channelService) {
        return new Builder(userService, channelService)
                .setId(dto.getId())
                .setOwner(dto.getOwnerId())
                .setChannel(dto.getChannelId())
                .setAccessTime(dto.getAccessTime())
                .build();
    }

    private static class Builder {
        private int id;
        private User owner;
        private Channel channel;
        private Date accessTime;

        private UserService userService;
        private ChannelService channelService;
        public Builder(UserService userService, ChannelService channelService) {
            this.userService = userService;
            this.channelService = channelService;
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setOwner(Integer ownerId) {
            this.owner = userService.findById(ownerId);
            return this;
        }

        public Builder setChannel(String channelId) {
            this.channel = channelService.findByDisplayId(channelId);
            return this;
        }

        public Builder setAccessTime(String accessTime) {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            try {
                this.accessTime = format.parse(accessTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return this;
        }

        public History build() {
            return new History(id, owner, channel, accessTime);
        }
    }
}
