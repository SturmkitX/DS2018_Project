package controller;

import dto.HistoryDTO;
import entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import service.HistoryService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resource/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/{id}")
    public List<HistoryDTO> getAll(@PathVariable(required = true, name = "id") int id) {
        List<History> histories = historyService.findByOwner(id);
        return histories.stream().map(a -> HistoryDTO.buildFromEntity(a)).collect(Collectors.toList());
    }

    @PostMapping("")
    public HistoryDTO addHistory(@RequestBody HistoryDTO dto) {
        // force the access time to be in server time (the client might be misconfigured)
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dto.setAccessTime(format.format(new Date(System.currentTimeMillis())));

        History history = historyService.buildHistoryFromDTO(dto);
        if (historyService.exists(history.getId())) {
            return null;
        }

        historyService.save(history);
        return HistoryDTO.buildFromEntity(history);
    }

    // may work, but shouldn't be used
    @DeleteMapping("/{id}")
    public HistoryDTO deleteHistory(@PathVariable(required = true, name = "id") int id) {
        return HistoryDTO.buildFromEntity(historyService.delete(id));
    }
}
