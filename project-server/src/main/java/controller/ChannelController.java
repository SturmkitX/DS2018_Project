package controller;

import dto.ChannelDTO;
import dto.UserDTO;
import entity.Channel;
import entity.History;
import entity.User;
import entity.enums.UserGender;
import exception.ChannelIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.*;
import utils.ChannelStatus;
import utils.PlaylistEntry;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resource/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelCategoryService categoryService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserGenderService userGenderService;

    @GetMapping("")
    public List<ChannelDTO> getAll() {
        return channelService.findActive().stream().map(a -> ChannelDTO.buildFromEntity(a))
                .collect(Collectors.toList());
    }

    @GetMapping("/{displayId}")
    public ChannelDTO getChannel(@PathVariable String displayId) {
        return ChannelDTO.buildFromEntity(channelService.findByDisplayId(displayId));
    }

    @GetMapping(value = "/user/{id}")
    public Map<String, String> getChannelByOwner(@PathVariable int id) {
        User user = userService.findById(id);
        Channel channel = channelService.findByOwner(user);

        Map<String, String> map = new HashMap<>();
        map.put("displayId", channel.getDisplayId());

        return map;
    }

    @GetMapping("/playlist/{displayId}")
    public List<PlaylistEntry> getPlaylist(@PathVariable String displayId) {
        List<PlaylistEntry> playlist = ChannelStatus.getPlaylist(displayId);
        return playlist == null ? new LinkedList<>() : playlist;
    }

    @PostMapping("/playlist/{displayId}/{index}")
    public PlaylistEntry insertPlayList(@PathVariable String displayId, @PathVariable int index, @RequestBody PlaylistEntry entry) {
        ChannelStatus.putPlaylist(displayId, entry, index);
        return entry;
    }

    @DeleteMapping("/playlist/{displayId}/{index}")
    public PlaylistEntry deletePlaylistIndex(@PathVariable String displayId, @PathVariable int index) {
        return ChannelStatus.deleteEntry(displayId, index);
    }

    @GetMapping("/category")
    public List<String> getAllCategories() {
        return categoryService.findAll().stream().map(a -> a.getCategory()).collect(Collectors.toList());
    }

    @GetMapping("/statistics/{displayId}")
    public Map<String, Integer> getStatistics(@PathVariable String displayId) {
        Map<String, Integer> stats = new HashMap<>();
        Channel channel = channelService.findByDisplayId(displayId);

        List<UserGender> genders = userGenderService.findAll();
        for (UserGender gnd : genders) {
            stats.put(gnd.getGender(), 0);
        }
        stats.put("Unspecified", 0);

        List<History> histories = historyService.findByChannel(channel);
        histories.stream().map(History::getOwner).distinct().forEach(a -> {
            UserGender genderType = a.getGender();
            String gender;
            if (genderType == null) {
                gender = "Unspecified";
            } else {
                gender = genderType.getGender();
            }
            Integer actual = stats.get(gender);
            stats.put(gender, actual + 1);
        });

        return stats;
    }

    // no POST method because a channel is created (but not activated) when a user account is created
    // and cannot be created in another context
    @PutMapping("")
    public ChannelDTO updateChannel(@RequestBody ChannelDTO dto) throws ChannelIdException {
        if (dto.getDisplayId().length() != 15) {
            throw new ChannelIdException();
        }
        Channel channel = Channel.buildFromDTO(dto, categoryService, userService);
        return ChannelDTO.buildFromEntity(channelService.save(channel));
    }

    // a channel can only be deleted when a user account is deleted
}
