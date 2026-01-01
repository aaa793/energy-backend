package dz.energy.energy_backend.service;

import dz.energy.energy_backend.model.User;
import dz.energy.energy_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public User findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public User save(User user) {
        return repo.save(user);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
