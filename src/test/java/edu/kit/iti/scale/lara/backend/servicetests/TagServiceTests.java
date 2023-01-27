package edu.kit.iti.scale.lara.backend.servicetests;

import edu.kit.iti.scale.lara.backend.ServiceTest;
import edu.kit.iti.scale.lara.backend.controller.repository.*;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@ServiceTest
public class TagServiceTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CachedPaperRepository cachedPaperRepository;
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private ResearchRepository researchRepository;
    @Autowired
    private SavedPaperRepository savedPaperRepository;
    @Autowired
    private UserCategoryRepository userCategoryRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindAll() {
        Tag tag1 = new Tag("#0000ff", "Test-Tag1", null);
        entityManager.persist(tag1);

        Tag tag2 = new Tag("#0000ff", "Test-Tag2", null);
        entityManager.persist(tag2);

        Tag tag3 = new Tag("#0000ff", "Test-Tag3", null);
        entityManager.persist(tag3);

        Iterable<Tag> tags = tagRepository.findAll();

        Assertions.assertThat(tags).hasSize(3).contains(tag1, tag2, tag3);
    }

}
