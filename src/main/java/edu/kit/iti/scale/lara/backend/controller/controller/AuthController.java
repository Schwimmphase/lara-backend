package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.record.LoginRequest;
import edu.kit.iti.scale.lara.backend.controller.service.AuthService;
import edu.kit.iti.scale.lara.backend.controller.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        if (userService.checkCredentials(request.password(), request.userId())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.userId(), request.password())
            );
            return authService.generateToken(authentication);
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
