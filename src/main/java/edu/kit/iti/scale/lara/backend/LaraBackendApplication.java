package edu.kit.iti.scale.lara.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("!nodb")
@SpringBootApplication
public class LaraBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaraBackendApplication.class, args);
    }

}
