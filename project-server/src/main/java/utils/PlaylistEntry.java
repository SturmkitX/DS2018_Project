package utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class PlaylistEntry {

    private String url;
    private Long duration;

    @JsonIgnore
    private Long startTime;

    public PlaylistEntry() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaylistEntry that = (PlaylistEntry) o;
        return url.equals(that.url) &&
                duration.equals(that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, duration);
    }
}
