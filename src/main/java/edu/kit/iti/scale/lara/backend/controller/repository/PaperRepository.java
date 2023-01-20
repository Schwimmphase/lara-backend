package edu.kit.iti.scale.lara.backend.controller.repository;

import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperRepository extends JpaRepository<Paper, String> {
}
