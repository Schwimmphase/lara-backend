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

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This class is used to handle everything that belongs to the context of the users
 *
 * @author ukgcc and unqkm
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CacheService cacheService;

    /**
     * Finds the user with given id from the UserRepository
     *
     * @param id the id of the user
     * @return the user with the given id
     * @throws NotInDataBaseException when there is no user with the id saved in the repository
     */
    public User getUserById(String id) throws NotInDataBaseException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotInDataBaseException();
        }
    }

    /**
     * Finds the user with given name from the repository
     *
     * @param name the name of the user to be found
     * @return the user with given name
     * @throws NotInDataBaseException when there is no user with this name saved in the repository
     */
    public User getUserByName(String name) throws NotInDataBaseException {
        Optional<User> user = userRepository.findByUsername(name);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotInDataBaseException();
        }
    }

    /**
     * Creates a new user with encoded password and saves him in the UserRepository
     *
     * @param username     the username of the user
     * @param password     the not decoded password of the user
     * @param userCategory the user-category of the user
     * @return the created user
     */
    public User createUser(String username, String password, UserCategory userCategory) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("A user with this username already exists");
        } else {
            password = passwordEncoder.encode(password);
            User user = new User(username, password, userCategory);
            userRepository.save(user);
            return user;
        }
    }

    /**
     * Is called everytime a user opens a new research different to the one he had opened before.
     * It loads all citations and references from SavedPapers that belong to the research into the CacheRepository and
     * sets the research as the users active research
     *
     * @param user     the user who opened ze research
     * @param research the research the user opened
     * @throws IOException when an error occurred getting the citations and references.
     */
    public void UserOpenedResearch(User user, Research research) throws IOException {
        cacheService.initializeCache(research);
        user.setActiveResearch(research);
        userRepository.save(user);
    }

    /**
     * Checks if the given password is the same as the users password
     *
     * @param password the password
     * @param username the username of the user
     * @return true if the password matches the users' password, else false
     */
    public boolean checkCredentials(String password, String username) {
        try {
            return passwordEncoder.matches(password, getUserByName(username).getPassword());

        } catch (NotInDataBaseException e) {
            return false;
        }
    }

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = getUserByName(username);
            List<GrantedAuthority> authorities = List.of();

            if (user.getUserCategory().getName().equals(UserCategory.ADMIN_CATEGORY)) {
                authorities = Collections.singletonList((GrantedAuthority) () -> SecurityConfig.ADMIN_AUTHORITY);
            }

            return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(), authorities);
        } catch (NotInDataBaseException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    /**
     * Finds all users that are saved in the UserRepository
     *
     * @return all users
     */
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Finds all users with a given user-category from the UserRepository
     *
     * @param userCategory the user-category to find users by
     * @return the users with the category
     */
    public List<User> getUsersByUserCategory(UserCategory userCategory) {
        return userRepository.findByUserCategory(userCategory);
    }

    /**
     * Deletes a user from the UserRepository
     *
     * @param user the user to be deleted
     */
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    /**
     * Updates a user by assigning a new name, a new password and a new user-category
     *
     * @param user        the user to be updated
     * @param newName     the new name
     * @param newPassword the new password
     * @param newCategory teh new user-category
     * @return the updated user
     */
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
