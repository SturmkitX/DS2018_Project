package service;

import dto.HistoryDTO;
import entity.Channel;
import entity.History;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.HistoryRepository;

import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    public List<History> findByOwner(int id) {
        User owner = userService.findById(id);
        return historyRepository.findTop15ByOwner(owner);
    }

    public List<History> findByChannel(Channel channel) {
        return historyRepository.findByChannel(channel);
    }

    public History save(History history) {
        return historyRepository.save(history);
    }

    public boolean exists(int id) {
        return historyRepository.existsById(id);
    }

    public History delete(int id) {
        History history = historyRepository.findById(id);
        historyRepository.delete(history);
        return history;
    }

    public History buildHistoryFromDTO(HistoryDTO dto) {
        return History.buildFromDTO(dto, userService, channelService);
    }
}
