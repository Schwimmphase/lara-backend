package edu.kit.iti.scale.lara.backend.organizertests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.controller.repository.AuthorRepository;
import edu.kit.iti.scale.lara.backend.model.organizer.Organizer;
import edu.kit.iti.scale.lara.backend.model.organizer.filtering.YearFilter;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@InMemoryTest
public class YearFilterTests {

    @Autowired
    AuthorRepository authorRepository;

    private final Organizer<Paper> noFilter = new YearFilter<>("0-" + Integer.MAX_VALUE);
    private final Organizer<Paper> exactFilter = new YearFilter<>("2012-2012");
    private final Organizer<Paper> twoThousandsFilter = new YearFilter<>("2000-2999");


    @Test
    public void testVenueFilter() {
        Author author = createAuthor();

        Paper paper1 = new Paper("id1", "paper1", 0, "abstract1",
                1, 0, "coolVenue", "url1", List.of(author));

        Paper paper2 = new Paper("id2", "paper2", -2002, "abstract2",
                2, 0, "epicVenue", "url2", List.of(author));

        Paper paper3 = new Paper("id3", "paper3", 3000, "abstract3",
                3, 0, "coolVenue", "url3", List.of(author));

        Paper paper4 = new Paper("id4", "paper4", 1999, "abstract4",
                4, 0, "randomVenue", "url4", List.of(author));

        Paper paper5 = new Paper("id5", "paper5", 2023, "abstract5",
                5, 0, "coolVenue", "url5", List.of(author));

        Paper paper6 = new Paper("id6", "paper6", 2999, "abstract6",
                6, 0, "epicVenue", "url6", List.of(author));

        Paper paper7 = new Paper("id7", "paper7", 2012, "abstract7",
                6, 0, "badassVenue", "url7", List.of(author));

        Paper paper8 = new Paper("id8", "paper8", 2000, "abstract8",
                7, 0, null, "url8", List.of(author));

        Paper paper9 = new Paper("id9", "paper9", Integer.MAX_VALUE, "abstract9",
                7, 0, "", "url9", List.of(author));


        List<Paper> papers = new ArrayList<>();
        papers.add(paper4);
        papers.add(paper2);
        papers.add(paper1);
        papers.add(paper7);
        papers.add(paper3);
        papers.add(paper6);
        papers.add(paper5);
        papers.add(paper9);
        papers.add(paper8);

        List<Paper> filteredPapers = noFilter.organize(papers);
        Assertions.assertThat(filteredPapers.size()).isEqualTo(8);

        for (Paper paper : filteredPapers) {
            Assertions.assertThat(paper.getYearPublished() >= 0).isEqualTo(true);
        }

        filteredPapers = exactFilter.organize(papers);
        Assertions.assertThat(filteredPapers.size()).isEqualTo(1);

        for (Paper paper : filteredPapers) {
            Assertions.assertThat(paper.getYearPublished()).isEqualTo(2012);
        }

        filteredPapers = twoThousandsFilter.organize(papers);
        Assertions.assertThat(filteredPapers.size()).isEqualTo(4);

        for (Paper paper : filteredPapers) {
            Assertions.assertThat(paper.getYearPublished() >= 2000).isEqualTo(true);
            Assertions.assertThat(paper.getYearPublished() < 3000).isEqualTo(true);
        }

    }

    private Author createAuthor() {
        Author author = new Author("testId", "test-author");
        authorRepository.save(author);

        Assertions.assertThat(authorRepository.findById(author.getId()).isPresent()).isEqualTo(true);
        Assertions.assertThat(authorRepository.findById(author.getId()).get()).isEqualTo(author);
        return author;
    }
}
