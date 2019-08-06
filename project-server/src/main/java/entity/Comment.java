package entity;

import dto.CommentDTO;
import service.ChannelService;
import service.UserService;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "channel_comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @ManyToOne(targetEntity = Channel.class)
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;

    @Column(name = "comment", nullable = false)
    private String content;

    @Column(name = "time")
    private Date time;

    public Comment() {
    }

    public Comment(int id, User author, Channel channel, String content, Date time) {
        this.id = id;
        this.author = author;
        this.channel = channel;
        this.content = content;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getContent() {
        return content;
    }

    public Date getTime() {
        return time;
    }

    public static Comment buildFromDTO(CommentDTO dto, UserService userService, ChannelService channelService) {
        Comment result = null;
        try {
            result =  new Builder(userService, channelService)
                    .setId(dto.getId())
                    .setAuthor(dto.getAuthorId())
                    .setChannel(dto.getChannelId())
                    .setContent(dto.getContent())
                    .setTime(dto.getTime())
                    .build();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static class Builder {
        private int id;
        private User author;
        private Channel channel;
        private String content;
        private Date time;

        private UserService userService;
        private ChannelService channelService;

        public Builder(UserService userService, ChannelService channelService) {
            this.userService = userService;
            this.channelService = channelService;
        }

        public Builder setId(Integer id) {
            if (id != null) {
                this.id = id;
            }

            return this;
        }

        public Builder setAuthor(Integer authorId) {
            this.author = userService.findById(authorId);
            return this;
        }

        public Builder setChannel(String channelId) {
            this.channel = channelService.findByDisplayId(channelId);
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setTime(String time) throws ParseException {
            if (time != null) {
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                this.time = format.parse(time);
            } else {
                this.time = new Date();
            }
            return this;
        }

        public Comment build() {
            return new Comment(id, author, channel, content, time);
        }
    }
}
