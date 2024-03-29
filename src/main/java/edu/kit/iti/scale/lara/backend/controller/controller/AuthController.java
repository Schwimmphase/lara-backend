package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.request.LoginRequest;
import edu.kit.iti.scale.lara.backend.controller.service.AuthService;
import edu.kit.iti.scale.lara.backend.controller.service.UserService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/**
 * This class contains the rest api endpoint for logging in.
 *
 * @author unqkm
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AuthService authService;

    /**
     * Login a user.
     *
     * @param request the login request containing the username and the password.
     * @return        the user and a jwt token. The jwt token is valid for 30 days and contains information about
     *                the username and if the user is an admin.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @NotNull LoginRequest request) {
        if (userService.checkCredentials(request.password(), request.username())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );

            try {
                User user = userService.getUserByName(request.username());
                boolean isAdmin = user.getUserCategory().getName().equals(UserCategory.ADMIN_CATEGORY);

                return ResponseEntity.ok(Map.of("token", authService.generateToken(authentication, isAdmin),
                        "user", user));
            } catch (NotInDataBaseException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
