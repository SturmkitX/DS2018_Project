package service;

import entity.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.EmailRepository;

import java.util.Collection;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    public List<Email> findAll() {
        return emailRepository.findAll();
    }

    public Email findById(int id) {
        return emailRepository.findById(id).orElse(null);
    }

    public Email save(Email email) { return emailRepository.save(email); }

    public void delete(Collection<Email> bulk) {
        emailRepository.deleteAll(bulk);
    }
}
