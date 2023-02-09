package edu.kit.iti.scale.lara.backend.controller.repository;

import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CachedPaperRepository extends JpaRepository<CachedPaper, CachedPaper.CachedPaperId> {

    List<CachedPaper> findByCachedPaperIdParentPaper(Paper parentPaper);

    List<CachedPaper> findByCachedPaperIdPaperOrParentPaper(Paper paper);

    List<CachedPaper> findByCachedPaperIdResearch(Research research);

    List<CachedPaper> findByCachedPaperIdResearchUser(User user);

    List<CachedPaper> deleteByCachedPaperIdResearchAndCachedPaperIdPaper(Research research, Paper paper);


}
