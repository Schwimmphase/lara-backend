package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.config.SecurityConfig;
import edu.kit.iti.scale.lara.backend.controller.repository.UserRepository;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
        password = passwordEncoder.encode(password);
        User user = new User(username, password, userCategory);
        userRepository.save(user);
        return user;
    }

    public void setActiveResearch(User user, Research research) {
        user.setActiveResearch(research);
        userRepository.save(user);
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
            List<GrantedAuthority> authorities = List.of();

            if (user.getUserCategory().getName().equals(UserCategory.ADMIN_CATEGORY)) {
                authorities = Collections.singletonList((GrantedAuthority) () -> SecurityConfig.ADMIN_AUTHORITY);
            }

            return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(), authorities);
        } catch (NotInDataBaseException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    public List<User> getUsers()  {
        return userRepository.findAll();
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public User updateUser(User user, String newName, String newPassword, UserCategory newCategory) {
        user.setUsername(newName);
        user.setUserCategory(newCategory);
        if (newPassword != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        userRepository.save(user);
        return user;
    }
}
