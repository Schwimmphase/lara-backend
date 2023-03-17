package edu.kit.iti.scale.lara.backend.repositorytests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.controller.repository.AuthorRepository;
import edu.kit.iti.scale.lara.backend.controller.service.PaperService;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaperType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@InMemoryTest
public class AuthorRepositoryTests {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PaperService paperService;

    @Test
    public void testSaveAuthorWithSameIds() {
        Author author1 = new Author("testId", "test-author1");
        authorRepository.save(author1);

        Author author2 = new Author("testId", "test-author2");
        authorRepository.save(author2);

        if (authorRepository.findById("testId").isPresent()) {
            Author returnedAuthor = authorRepository.findById("testId").get();
            Assertions.assertThat(returnedAuthor.getName().equals("test-author2")).isEqualTo(true);
        }
        authorRepository.deleteAll();
        //Assertions.assertThat(authorRepository.findAll().isEmpty()).isEqualTo(true);

        Paper paper1 = new Paper("id1", "paper1", 2023, "abstract",
                0, 0, "venue", "url", List.of(author1));
        paperService.savePaperToDataBase(paper1);

        Assertions.assertThat(authorRepository.findById("testId").get().getName()).isEqualTo("test-author1");

        Paper paper2 = new Paper("id2", "paper2", 2023, "abstract",
                0, 0, "venue", "url", List.of(author2));
        paperService.savePaperToDataBase(paper2);

        Assertions.assertThat(authorRepository.findById("testId").get().getName()).isEqualTo("test-author2");

    }
}
