package edu.kit.iti.scale.lara.backend.controller.apicontroller;

import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ApiPaperConverterTest {

    private static ApiPaperConverter apiPaperConverter = new ApiPaperConverter();

    // create APiPaper
    // set apiPaper attributes
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
    // create test ApiPaper
    private static final ApiPaper TEST_API_PAPER = new ApiPaper(List.of(AUTHOR_ONE, AUTHOR_TWO, AUTHOR_THREE), PAPER_ID, TITLE, YEAR_PUBLISHED, ABSTRACT_TEST, CITATION_COUNT, REFERENCE_COUNT, VENUE, PDF_URL);


    @Test
    void testConvert() {

        // execute
        Paper paper = apiPaperConverter.convert(TEST_API_PAPER);

        // test
        Assertions.assertEquals(PAPER_ID, paper.getPaperId());
        Assertions.assertEquals(TITLE, paper.getTitle());
        Assertions.assertEquals(YEAR_PUBLISHED, paper.getYearPublished());
        Assertions.assertEquals(ABSTRACT_TEST, paper.getAbstractText());
        Assertions.assertEquals(CITATION_COUNT, paper.getCitationCount());
        Assertions.assertEquals(REFERENCE_COUNT, paper.getReferenceCount());
        Assertions.assertEquals(VENUE, paper.getVenue());
        Assertions.assertEquals(PDF_URL, paper.getPdfUrl());
        Assertions.assertEquals(List.of(AUTHOR_ONE, AUTHOR_TWO, AUTHOR_THREE), paper.getAuthors());
    }


}
