package edu.kit.iti.scale.lara.backend.controller.repository;

import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CachedPaperRepository  extends JpaRepository<CachedPaper, CachedPaper.CachedPaperId> {

    List<CachedPaper> findByParentPaper(Paper parentPaper);

}
