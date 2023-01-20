package edu.kit.iti.scale.lara.backend.controller.repository;

import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CachedPaperRepository  extends JpaRepository<CachedPaper, CachedPaper.CachedPaperId> {
}
