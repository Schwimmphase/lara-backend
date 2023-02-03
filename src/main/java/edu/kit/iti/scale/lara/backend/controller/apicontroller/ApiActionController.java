package edu.kit.iti.scale.lara.backend.controller.apicontroller;

import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import edu.kit.iti.scale.lara.backend.api.semanticscolar.SemanticScholarHandler;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import org.json.JSONException;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@Controller
public class ApiActionController {

    private SemanticScholarHandler semanticScholarHandler = new SemanticScholarHandler();
    private ApiPaperConverter apiPaperConverter = new ApiPaperConverter();

    public Paper getPaper(String paperId) throws JSONException, IOException {

        ApiPaper apiPaper = semanticScholarHandler.getPaper(paperId);
        Paper paper = apiPaperConverter.convert(apiPaper);

        return paper;
    }

    public List<Paper> getPapersByKeyword(String query) throws IOException, JSONException {

        List<ApiPaper> apiPapers = semanticScholarHandler.getPapersByKeyword(query);

        List<Paper> papers = new ArrayList<>();

        // convert resulting Api papers to papers
        for (ApiPaper apiPaper : apiPapers) {
            papers.add(apiPaperConverter.convert(apiPaper));
        }

        return papers;
    }

    public List<Paper> getRecommendations(List<Paper> positives, List<Paper> negatives) throws IOException, JSONException {

        List<String> positiveIds = new ArrayList<>();
        List<String> negativeIds = new ArrayList<>();

        // get Ids from positive papers
        for (Paper paper : positives) {
            positiveIds.add(paper.getPaperId());
        }

        // get Ids from negative papers
        for (Paper paper : negatives) {
            negativeIds.add(paper.getPaperId());
        }

        List<ApiPaper> recommendedApiPapers = semanticScholarHandler.getRecommendations(positiveIds, negativeIds);

        List<Paper> recommendedPapers = new ArrayList<>();

        // convert recommended Api papers to papers
        for (ApiPaper apiPaper : recommendedApiPapers) {
            recommendedPapers.add(apiPaperConverter.convert(apiPaper));
        }

        return recommendedPapers;
    }

    public List<Paper> getCitations(Paper paper) throws IOException, JSONException {

        List<ApiPaper> citationsApiPapers = semanticScholarHandler.getCitations(paper.getPaperId());

        List<Paper> citations = new ArrayList<>();

        // convert citations from Api paper to paper
        for (ApiPaper apiPaper : citationsApiPapers) {
            citations.add(apiPaperConverter.convert(apiPaper));
        }

        return citations;
    }

    public List<Paper> getReferences(Paper paper) throws IOException, JSONException {

        List<ApiPaper> referencesApiPaper = semanticScholarHandler.getReferences(paper.getPaperId());

        List<Paper> references = new ArrayList<>();

        // convert citations from Api paper to paper
        for (ApiPaper apiPaper : referencesApiPaper) {
            references.add(apiPaperConverter.convert(apiPaper));
        }

        return references;
    }
}
