package edu.kit.iti.scale.lara.backend;

import edu.kit.iti.scale.lara.backend.controller.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class LaraBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaraBackendApplication.class, args);
    }

}
