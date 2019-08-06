package service;

import entity.enums.ChannelCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ChannelCategoryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChannelCategoryService {
    private static Map<String, ChannelCategory> categories = new HashMap<>();

    @Autowired
    private ChannelCategoryRepository categoryRepository;

    public ChannelCategory findByCategory(String category) {
        if (categories.get(category) == null) {
            categories.put(category, categoryRepository.findByCategory(category));
        }
        return categories.get(category);
    }

    public List<ChannelCategory> findAll() {
        return categoryRepository.findAll();
    }
}
