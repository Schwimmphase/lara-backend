package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.repository.CachedPaperRepository;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaperType;

import java.util.ArrayList;
import java.util.List;

public class CacheService {

    private final CachedPaperRepository cachedPaperRepository;

    public CacheService(CachedPaperRepository cachedPaperRepository) {
        this.cachedPaperRepository = cachedPaperRepository;
    }

    public void initializeCache(Research research) {
        //TODO
    }

    public void flushCacheResearch(Research research) {

        // TODO

    }

    public void addPaper(Research research, Paper paper, Paper parent) {

        // TODO

    }

    public void removePaper(Research research) {

        // TODO

    }

    public List<Paper> getReferences(Research research, List<Paper> papers) { //Todo maybe findByResearch
        List<Paper> references = new ArrayList<>();
        for (Paper parentPaper : papers) {
            for (CachedPaper cachedPaper : cachedPaperRepository.findByParentPaper(parentPaper)) {
                if (cachedPaper.getType() == CachedPaperType.REFERENCE) {
                    references.add(cachedPaper.getPaper());
                }
            }
        }
        return references;
    }

    public List<Paper> getCitations(Research research, List<Paper> papers) { //Todo maybe findByResearch
        List<Paper> citations = new ArrayList<>();
        for (Paper parentPaper : papers) {
            for (CachedPaper cachedPaper : cachedPaperRepository.findByParentPaper(parentPaper)) {
                if (cachedPaper.getType() == CachedPaperType.CITATION) {
                    citations.add(cachedPaper.getPaper());
                }
            }
        }
        return citations;
    }
}
