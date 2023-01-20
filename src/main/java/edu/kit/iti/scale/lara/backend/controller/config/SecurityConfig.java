package edu.kit.iti.scale.lara.backend.controller.config;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {

    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        // TODO

        return null;
    }

    public JwtDecoder jwtDecoder() {

        // TODO

        return null;
    }

    public JwtEncoder jwtEncoder() {

        // TODO

        return null;
    }

    public AuthenticationManager authenticationManager() {

        // TODO

        return null;
    }
}
