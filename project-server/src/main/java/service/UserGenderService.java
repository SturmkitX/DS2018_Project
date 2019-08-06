package service;

import entity.enums.UserGender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserGenderRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserGenderService {
    private static Map<String, UserGender> genders = new HashMap<>();

    @Autowired
    private UserGenderRepository genderRepository;

    public UserGender findByGender(String gender) {
        if (genders.get(gender) == null) {
            genders.put(gender, genderRepository.findByGender(gender));
        }
        return genders.get(gender);
    }

    public List<UserGender> findAll() {
        List<UserGender> result = genderRepository.findAll();
        return result;
    }
}
