package dto;

import entity.Channel;
import entity.Comment;
import entity.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentDTO {

    private Integer id;
    private String authorName;
    private Integer authorId;
    private String channelId;
    private String content;
    private String time;

    public CommentDTO() {
    }

    public CommentDTO(Integer id, String authorName, Integer authorId, String channelId, String content, String time) {
        this.id = id;
        this.authorName = authorName;
        this.authorId = authorId;
        this.channelId = channelId;
        this.content = content;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public static CommentDTO buildFromEntity(Comment comment) {
        return new Builder()
                .setAuthorName(comment.getAuthor())
                .setChannelId(comment.getChannel())
                .setContent(comment.getContent())
                .setId(comment.getId())
                .setTime(comment.getTime())
                .build();
    }

    private static class Builder {
        private Integer id;
        private String authorName;
        private Integer authorId;
        private String channelId;
        private String content;
        private String time;

        public Builder() {
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setAuthorName(User author) {
            this.authorName = author.getName();
            this.authorId = author.getId();
            return this;
        }

        public Builder setChannelId(Channel channel) {
            this.channelId = channel.getDisplayId();
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setTime(Date time) {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            this.time = format.format(time);
            return this;
        }

        public CommentDTO build() {
            return new CommentDTO(id, authorName, authorId, channelId, content, time);
        }
    }
}
