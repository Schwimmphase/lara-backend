package edu.kit.iti.scale.lara.backend.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedPaperRepository extends JpaRepository<Void, Void> {

    List<SavedPaper> findByResearch(Research research);
}
