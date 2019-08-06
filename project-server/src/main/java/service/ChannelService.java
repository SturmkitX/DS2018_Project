package service;

import dto.ChannelDTO;
import entity.Channel;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ChannelRepository;

import java.util.List;

@Service
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelCategoryService categoryService;

    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    public Channel fyndById(int id) {
        return channelRepository.findById(id);
    }

    public Channel findByDisplayId(String displayId) { return channelRepository.findByDisplayId(displayId); }

    public Channel findByOwner(User owner) {
        return channelRepository.findByOwner(owner);
    }

    public Channel save(Channel channel) {
        return channelRepository.save(channel);
    }

    public Channel buildChannelFromDTO(ChannelDTO dto) {
        return Channel.buildFromDTO(dto, categoryService, userService);
    }

    public List<Channel> findActive() {
        return channelRepository.findByActive(true);
    }
}
