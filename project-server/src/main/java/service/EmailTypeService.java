package service;

import entity.enums.EmailType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.EmailTypeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailTypeService {
    private static Map<String, EmailType> types = new HashMap<>();

    @Autowired
    private EmailTypeRepository typeRepository;

    public EmailType findByName(String name) {
        if (types.get(name) == null) {
            types.put(name, typeRepository.findByName(name));
        }
        return types.get(name);
    }

    public List<EmailType> findAll() {
        return typeRepository.findAll();
    }
}
