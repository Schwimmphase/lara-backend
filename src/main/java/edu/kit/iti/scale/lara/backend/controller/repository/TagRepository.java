package edu.kit.iti.scale.lara.backend.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Void, Void> {

    List<Tag> findByResearch(Research research);
}
