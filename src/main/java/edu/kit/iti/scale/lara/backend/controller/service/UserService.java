package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.repository.UserRepository;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User getUserById(String id) throws NotInDataBaseException {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        } else {
            throw new NotInDataBaseException();
        }
    }

    public User createUser(String username, String password, UserCategory userCategory) {
        User user = new User(username, passwordEncoder.encode(password), userCategory);
        userRepository.save(user);
        return user;
    }

    public boolean checkCredentials(String password, String userId) {
        try {
            return passwordEncoder.matches(password, getUserById(userId).getPassword());

        } catch (NotInDataBaseException e) {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        try {
            User user = getUserById(userId);
            return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(),
                    Collections.emptyList()); // TODO: Admin Authority
        } catch (NotInDataBaseException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
