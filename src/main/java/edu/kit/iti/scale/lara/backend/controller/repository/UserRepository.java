package edu.kit.iti.scale.lara.backend.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Void, Void> {

    User findByUsername(String username);

}
