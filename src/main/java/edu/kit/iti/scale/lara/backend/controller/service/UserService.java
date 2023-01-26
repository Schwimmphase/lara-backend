package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.repository.UserRepository;
import edu.kit.iti.scale.lara.backend.model.user.User;
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

    public User readUserById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User with this Id not found");
        }
        return user.get();
    }

    public boolean checkCredentials(String password, String userId) {
        return passwordEncoder.matches(password, readUserById(userId).getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = readUserById(userId);
        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(),
                Collections.emptyList()); // TODO: Admin Authority
    }
}
