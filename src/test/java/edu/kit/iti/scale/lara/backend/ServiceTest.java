package edu.kit.iti.scale.lara.backend;

import edu.kit.iti.scale.lara.backend.controller.config.RsaKeyProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest
@ActiveProfiles("test")
@EnableConfigurationProperties(RsaKeyProperties.class)
@TestPropertySource("classpath:application.properties")
public @interface ServiceTest {
}
