package edu.kit.iti.scale.lara.backend.controller.repository;

import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedPaperRepository extends JpaRepository<Void, Void> {

    List<SavedPaper> findByResearch(Research research);
}
