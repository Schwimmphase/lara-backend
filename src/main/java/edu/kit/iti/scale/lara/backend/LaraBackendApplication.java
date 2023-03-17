package edu.kit.iti.scale.lara.backend;

import edu.kit.iti.scale.lara.backend.controller.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * This class represents the main class of lara backend.
 *
 * @author Johannes Breitling (udqoi), Linus Buck (uefjv), Gregor Czubayko (unqkm), Paul Gauer (ukgcc),
 *         Thomas Roth (uruox)
 */
@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class LaraBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaraBackendApplication.class, args);
    }

}
