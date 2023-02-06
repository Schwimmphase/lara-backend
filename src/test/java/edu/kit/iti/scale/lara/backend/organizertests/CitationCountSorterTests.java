package edu.kit.iti.scale.lara.backend.organizertests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.controller.repository.AuthorRepository;
import edu.kit.iti.scale.lara.backend.model.organizer.Organizer;
import edu.kit.iti.scale.lara.backend.model.organizer.sorting.CitationCountSorter;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@InMemoryTest
public class CitationCountSorterTests {

    @Autowired
    AuthorRepository authorRepository;

    private final Organizer<Paper> citationCountSorter = new CitationCountSorter<>("ascending");

//    @Test
    public void testCitationCountSorter() {
        Author author = createAuthor();

        Paper paper1 = new Paper("id1", "paper1", 2023, "abstract1",
                1, 0, "venue1", "url1", List.of(author));

        Paper paper2 = new Paper("id2", "paper2", 2023, "abstract2",
                2, 0, "venue2", "url2", List.of(author));

        Paper paper3 = new Paper("id3", "paper3", 2023, "abstract3",
                3, 0, "venue3", "url3", List.of(author));

        Paper paper4 = new Paper("id4", "paper4", 2023, "abstract4",
                4, 0, "venue4", "url4", List.of(author));

        Paper paper5 = new Paper("id5", "paper5", 2023, "abstract5",
                5, 0, "venue5", "url5", List.of(author));

        Paper paper6 = new Paper("id6", "paper6", 2023, "abstract6",
                6, 0, "venue6", "url6", List.of(author));

        Paper paper7 = new Paper("id7", "paper7", 2023, "abstract7",
                6, 0, "venue7", "url7", List.of(author));

        Paper paper8 = new Paper("id8", "paper8", 2023, "abstract8",
                7, 0, "venue8", "url8", List.of(author));

        Paper paper9 = new Paper("id9", "paper9", 2023, "abstract9",
                7, 0, "venue9", "url9", List.of(author));

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

        List<Paper> sortedPapers = citationCountSorter.organize(papers);

        for (int i = 1; i < sortedPapers.size(); i++)
            Assertions.assertThat(sortedPapers.get(i).getCitationCount()).isGreaterThanOrEqualTo
                    (sortedPapers.get(i - 1).getCitationCount());
    }

    private Author createAuthor() {
        Author author = new Author("testId", "test-author");
        authorRepository.save(author);

        Assertions.assertThat(authorRepository.findById(author.getId()).isPresent()).isEqualTo(true);
        Assertions.assertThat(authorRepository.findById(author.getId()).get()).isEqualTo(author);
        return author;
    }
}
