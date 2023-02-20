package edu.kit.iti.scale.lara.backend.controller.apicontroller;

import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiActionControllerTest {

    private static ApiActionController testApiActionController = new ApiActionController();

    // set paper attributes
    private static final String PAPER_ID = "S2$443ee74236492a8863eacaf9fabbedf30827cc55";
    private static final String TITLE = "title-test";
    private static final int YEAR_PUBLISHED = 2014;
    private static final String ABSTRACT_TEST = "abstract-test";
    private static final int CITATION_COUNT = 69;
    private static final int REFERENCE_COUNT = 420;
    private static final String VENUE = "venue-test";
    private static final String PDF_URL = "https://journals.plos.org/plosone/article/file?id=10.1371/journal.pone.0098679&type=printable";
    // set author attributes
    private static final String AUTHOR_ID_ONE = "1933458";
    private static final String NAME_ONE = "author one";
    private static final Author AUTHOR_ONE = new Author(AUTHOR_ID_ONE, NAME_ONE);
    private static final String AUTHOR_ID_TWO = "1933458";
    private static final String NAME_TWO = "author two";
    private static final Author AUTHOR_TWO = new Author(AUTHOR_ID_TWO, NAME_TWO);
    private static final String AUTHOR_ID_THREE = "2093416293";
    private static final String NAME_THREE = "author three";
    private static final Author AUTHOR_THREE = new Author(AUTHOR_ID_THREE, NAME_THREE);
    // create test Paper
    private static final Paper TEST_PAPER = new Paper(PAPER_ID, TITLE, YEAR_PUBLISHED, ABSTRACT_TEST, CITATION_COUNT, REFERENCE_COUNT, VENUE, PDF_URL, List.of(AUTHOR_ONE, AUTHOR_TWO, AUTHOR_THREE));

    // create testPaper list
    private static final List<Paper> TEST_PAPER_LIST = List.of(TEST_PAPER, TEST_PAPER, TEST_PAPER, TEST_PAPER, TEST_PAPER, TEST_PAPER, TEST_PAPER, TEST_PAPER, TEST_PAPER, TEST_PAPER);



    private static ApiActionController apiActionController = mock(ApiActionController.class);

    public static ApiActionController getApiActionController() {
        return apiActionController;
    }



    public static void getPaper() throws IOException {
        when(apiActionController.getPaper(anyString())).thenReturn(TEST_PAPER);

    }


    public static void getPapersByKeyword() throws IOException {
        when(apiActionController.getPapersByKeyword(anyString())).thenReturn(TEST_PAPER_LIST);

    }

    public static void getCitations() throws IOException {
        when(apiActionController.getCitations(any(Paper.class))).thenReturn(TEST_PAPER_LIST);
    }


    public static void getReferences() throws IOException {
        when(apiActionController.getReferences(any(Paper.class))).thenReturn(TEST_PAPER_LIST);
    }


    public static void getRecommendations() throws IOException {
        when(apiActionController.getRecommendations(ArgumentMatchers.anyList(), ArgumentMatchers.anyList())).thenReturn(TEST_PAPER_LIST);
    }

    // create Paper to test getCitations, getReferences & getRecommendations
    Paper paperOne = new Paper("S2$158a78161dbddbf81b1c77701cab9211acab8872", null, 420, null, 69, 123, null, null, null);
    Paper paperTwo = new Paper("S2$98d4e346a0be0c46b51774b2f0c54e9b529f510b", null, 420, null, 69, 123, null, null, null);

    @Test
    void testGetPaper() throws IOException {

        // set up
        String laraId = "S2$443ee74236492a8863eacaf9fabbedf30827cc55";

        // execute
        Paper paper = testApiActionController.getPaper(laraId);

        // test
        Assertions.assertNotNull(paper.getPaperId());
        Assertions.assertNotNull(paper.getTitle());
        Assertions.assertNotEquals(0, paper.getYearPublished());
        Assertions.assertNotNull(paper.getAbstractText());
        Assertions.assertNotEquals(0, paper.getCitationCount());
        Assertions.assertNotEquals(0, paper.getReferenceCount());
        Assertions.assertNotNull(paper.getVenue());
        Assertions.assertNotNull(paper.getPdfUrl());
        Assertions.assertNotNull(paper.getAuthors());

    }

    @Test
    void testGetPapersByKeyword() throws IOException {

        // set up
        String query = "np problems";

        // execute
        List<Paper> papers = testApiActionController.getPapersByKeyword(query);

        // test
        for (Paper paper : papers) {
            Assertions.assertNotNull(paper.getPaperId());
            Assertions.assertNotNull(paper.getTitle());
        }

    }

    @Test
    void testGetCitations() throws IOException {

        // execute
        List<Paper> papers = testApiActionController.getCitations(paperOne);

        // test
        for (Paper paper : papers) {
            Assertions.assertNotNull(paper.getPaperId());
            Assertions.assertNotNull(paper.getTitle());
        }

    }

    @Test
    void testGetReferences() throws IOException {

        // execute
        List<Paper> papers = testApiActionController.getReferences(paperOne);

        // test
        for (Paper paper : papers) {
            Assertions.assertNotNull(paper.getPaperId());
            Assertions.assertNotNull(paper.getTitle());
        }
    }

    @Test
    void testGetRecommendations() throws IOException {

        // execute
        List<Paper> papers = testApiActionController.getRecommendations(List.of(paperOne), List.of(paperTwo));

        // test
        for (Paper paper : papers) {
            Assertions.assertNotNull(paper.getPaperId());
            Assertions.assertNotNull(paper.getTitle());
        }
    }

}