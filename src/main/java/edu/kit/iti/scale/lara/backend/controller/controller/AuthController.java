package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.record.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    public BCryptPasswordEncoder passwordEncoder;
    public AuthenticationManager authenticationManager;

    public String login(LoginRequest request) {

        // TODO

        return "loginResponse";
    }
}
