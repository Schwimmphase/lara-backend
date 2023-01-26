package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.repository.UserRepository;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String username, String password, UserCategory userCategory) {
        User user = new User(username, password, userCategory);
        userRepository.save(user);
    }

    public User getUserById(String id) throws NotInDataBaseException {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        } else {
            throw new NotInDataBaseException();
        }
    }

    public boolean checkCredentials(User user, String password) {
        return user.getPassword().equals(password);
    }
}
