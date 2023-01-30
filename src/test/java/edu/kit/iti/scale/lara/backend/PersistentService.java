package edu.kit.iti.scale.lara.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersistentService {

    private final TestEntityManager entityManager;

    public void persist(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                throw new IllegalArgumentException("Object to persist is null");
            }

            entityManager.persist(object);
        }
    }

}