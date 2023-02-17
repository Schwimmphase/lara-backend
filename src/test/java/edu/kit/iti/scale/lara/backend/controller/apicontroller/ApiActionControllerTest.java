package edu.kit.iti.scale.lara.backend.controller.apicontroller;

import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import org.hamcrest.Matchers;
import org.mockito.ArgumentMatchers;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiActionControllerTest {

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



    public static void testGetPaper() throws IOException {
        when(apiActionController.getPaper(anyString())).thenReturn(TEST_PAPER);

    }


    public static void testGetPapersByKeyword() throws IOException {
        when(apiActionController.getPapersByKeyword(anyString())).thenReturn(TEST_PAPER_LIST);

    }

    public static void testGetCitations() throws IOException {
        when(apiActionController.getCitations(any(Paper.class))).thenReturn(TEST_PAPER_LIST);
    }


    public static void testGetReferences() throws IOException {
        when(apiActionController.getReferences(any(Paper.class))).thenReturn(TEST_PAPER_LIST);
    }


    public static void testGetRecommendations() throws IOException {
        when(apiActionController.getRecommendations(anyList(), anyList())).thenReturn(TEST_PAPER_LIST);
    }

}