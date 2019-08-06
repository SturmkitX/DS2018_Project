package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

public class ChannelStatus {

    private static Map<String, List<PlaylistEntry>> status = new HashMap<>();
    private static Map<String, List<WebSocketSession>> subscribers = new HashMap<>();

    private static Object statusLock = new Object();
    private static Object subsLock = new Object();

    private static Timer timer = new Timer();

    private ChannelStatus() {}

    public static Set<String> getKeys() {
        return status.keySet();
    }

    public static List<PlaylistEntry> getPlaylist(String key) {
        return status.get(key);
    }

    public static void putPlaylist(String key, PlaylistEntry value, int index) {
        synchronized (statusLock) {
            if (status.get(key) == null) {
                // start playback right away
                value.setStartTime(System.currentTimeMillis());
                List<PlaylistEntry> result = new LinkedList<>();
                result.add(index, value);
                status.put(key, result);

                // add an extra 0.5 sec to the APPROXIMATED end of the video
                timer.schedule(new PlaylistTimerTask(key),value.getDuration() * 1000 + 500);
            } else {
                status.get(key).add(index, value);
                if (status.get(key).size() == 1) {
                    value.setStartTime(System.currentTimeMillis());

                    // add an extra 0.5 sec to the APPROXIMATED end of the video
                    timer.schedule(new PlaylistTimerTask(key),value.getDuration() * 1000 + 500);
                }
            }
        }
    }

    public static PlaylistEntry deleteEntry(String key, int index) {
        PlaylistEntry result = null;
        synchronized (statusLock) {
            if (status.get(key) == null) {
                return null;
            }

            result = status.get(key).remove(index);
            if (!status.get(key).isEmpty() && index == 0) {
                // we have a new first video
                PlaylistEntry entry = status.get(key).get(0);
                entry.setStartTime(System.currentTimeMillis());
                // add an extra 0.5 sec to the APPROXIMATED end of the video
                timer.schedule(new PlaylistTimerTask(key), entry.getDuration() * 1000 + 500);
            }
        }
        return result;
    }

    public static List<WebSocketSession> getSubscribers(String key) {
        return subscribers.get(key);
    }

    public static void insertSubscriber(String key, WebSocketSession value) {
        synchronized (subsLock) {
            if (subscribers.get(key) == null) {
                List<WebSocketSession> result = new LinkedList<>();
                result.add(value);
                subscribers.put(key, result);
            } else {
                subscribers.get(key).add(value);
            }
        }
    }

    // should be called periodically
    public static void purgeDisconnected() {
        System.out.println("Purging lost connections ...");
        int oldSize = subscribers.values().stream().mapToInt(Collection::size).sum();
        synchronized (subsLock) {
            Set<String> keys = subscribers.keySet();
            for (String key : keys) {
                subscribers.get(key).removeIf(a -> !a.isOpen());
            }
        }
        int newSize = subscribers.values().stream().mapToInt(Collection::size).sum();
        System.out.println(String.format("Purging results: Before: %d; After: %d", oldSize, newSize));
    }

    // should be called periodically
    public static void synchronizePlaylists() throws IOException {
        synchronized (subsLock) {
            Set<String> keys = subscribers.keySet();
            for (String key : keys) {
                List<PlaylistEntry> playlist = status.get(key);
                if (playlist == null || playlist.isEmpty()) {
                    continue;
                }

                PlaylistEntry first = playlist.get(0);
                ClientMessage message = new ClientMessage();
                message.setType("SYNC");
                message.setChannel(key);
                float timePassed = 1.0f * (System.currentTimeMillis() - first.getStartTime()) / 1000;
                message.setContent(String.format("%s %f", first.getUrl(), timePassed));
                ObjectMapper mapper = new ObjectMapper();
                byte[] serialized = mapper.writeValueAsBytes(message);

                // we are guaranteed to have a working list prepared (not necessarily populated)
                List<WebSocketSession> subs = subscribers.get(key);
                for (WebSocketSession session : subs) {
                    if (session.isOpen()) {
                        session.sendMessage(new TextMessage(serialized));
                    }

                }
            }
        }
    }

    private static class PlaylistTimerTask extends TimerTask {

        private String channel;

        public PlaylistTimerTask(String channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            synchronized (statusLock) {
                List<PlaylistEntry> playlist = status.get(channel);
                if (playlist.isEmpty()) {
                    return;
                }

                playlist.remove(0);

                if (playlist.isEmpty()) {
                    return;
                }
                PlaylistEntry first = playlist.get(0);

                first.setStartTime(System.currentTimeMillis());
                timer.schedule(new PlaylistTimerTask(channel), first.getDuration() * 1000 + 500);
                try {
                    synchronizePlaylists();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
