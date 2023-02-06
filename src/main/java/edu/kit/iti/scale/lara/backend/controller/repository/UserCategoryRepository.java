package edu.kit.iti.scale.lara.backend.controller.repository;

import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCategoryRepository extends JpaRepository<UserCategory, String> {

    List<UserCategory> findByName(String name);

    boolean existsByName(String name);
}
