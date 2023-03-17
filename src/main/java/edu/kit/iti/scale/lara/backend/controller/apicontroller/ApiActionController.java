package edu.kit.iti.scale.lara.backend.controller.apicontroller;

import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import edu.kit.iti.scale.lara.backend.api.semanticscolar.SemanticScholarHandler;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import org.json.JSONException;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ApiActionController {

    private final SemanticScholarHandler semanticScholarHandler = new SemanticScholarHandler();
    private final ApiPaperConverter apiPaperConverter = new ApiPaperConverter();

    public Paper getPaper(String paperId) throws JSONException, IOException {
        ApiPaper apiPaper = semanticScholarHandler.getPaper(paperId);

        return apiPaperConverter.convert(apiPaper);
    }

    public List<Paper> getPapersByKeyword(String query) throws IOException, JSONException {
        List<ApiPaper> apiPapers = semanticScholarHandler.getPapersByKeyword(query);

        // convert resulting Api papers to papers
        return apiPapers.stream().map(apiPaperConverter::convert).collect(Collectors.toList());
    }

    public List<Paper> getRecommendations(List<Paper> positives, List<Paper> negatives) throws IOException, JSONException {
        // get Ids from positive papers
        List<String> positiveIds = positives.stream().map(Paper::getPaperId).collect(Collectors.toList());

        // get Ids from negative papers
        List<String> negativeIds = negatives.stream().map(Paper::getPaperId).collect(Collectors.toList());

        List<ApiPaper> recommendedApiPapers = semanticScholarHandler.getRecommendations(positiveIds, negativeIds);

        // convert recommended Api papers to papers
        return recommendedApiPapers.stream().map(apiPaperConverter::convert).collect(Collectors.toList());
    }

    public List<Paper> getCitations(Paper paper) throws IOException, JSONException {
        List<ApiPaper> citationsApiPapers = semanticScholarHandler.getCitations(paper.getPaperId());

        // convert citations from Api paper to paper
        return citationsApiPapers.stream().map(apiPaperConverter::convert).collect(Collectors.toList());
    }

    public List<Paper> getReferences(Paper paper) throws IOException, JSONException {
        List<ApiPaper> referencesApiPaper = semanticScholarHandler.getReferences(paper.getPaperId());

        // convert citations from Api paper to paper
        return referencesApiPaper.stream().map(apiPaperConverter::convert).collect(Collectors.toList());
    }
}
