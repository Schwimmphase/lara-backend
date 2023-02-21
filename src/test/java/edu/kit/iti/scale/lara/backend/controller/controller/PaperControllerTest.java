package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.apicontroller.ApiActionController;
import edu.kit.iti.scale.lara.backend.controller.config.RsaKeyProperties;
import edu.kit.iti.scale.lara.backend.controller.config.SecurityConfig;
import edu.kit.iti.scale.lara.backend.controller.config.WebConfig;
import edu.kit.iti.scale.lara.backend.controller.service.PaperService;
import edu.kit.iti.scale.lara.backend.controller.service.ResearchService;
import edu.kit.iti.scale.lara.backend.controller.service.TagService;
import edu.kit.iti.scale.lara.backend.controller.service.UserService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaperController.class)
@Import(PaperController.class)
@ContextConfiguration(classes = {SecurityConfig.class, WebConfig.class})
@EnableConfigurationProperties(RsaKeyProperties.class)
class PaperControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    ResearchService researchService;

    @MockBean
    PaperService paperService;

    @MockBean
    UserService userService;

    @MockBean
    ApiActionController apiActionController;

    @MockBean
    TagService tagService;



    @Test
    public void testPaperDetails() throws Exception {

        User user = mockUser();

        Research research = mockResearch(user);

        Paper paper = mockPaper();

        SavedPaper savedPaper = new SavedPaper(paper, research, new Comment("test-comment"), 3, SaveState.ADDED);

        research.addSavedPaper(savedPaper);

        given(researchService.getResearch(anyString(), eq(user))).willReturn(research);
        given(paperService.getPaper(eq("test-id"))).willReturn(paper);
        given(paperService.getSavedPaper(eq(user), eq(paper), eq(research))).willReturn(savedPaper);


        mvc.perform(get("/paper/test-id")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("researchId", "test-researchId")
                                .requestAttr("user", user)
                                .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paper.paperId").value("test-id"))
                .andExpect(jsonPath("$.paper.title").value("test-title"))
                .andExpect(jsonPath("$.paper.year").value(2023))
                .andExpect(jsonPath("$.paper.abstract").value("test-abstract"))
                .andExpect(jsonPath("$.paper.citationCount").value(69))
                .andExpect(jsonPath("$.paper.referenceCount").value(420))
                .andExpect(jsonPath("$.paper.venue").value("test-venue"))
                .andExpect(jsonPath("$.paper.pdfUrl").value("test-url"))
                .andExpect(jsonPath("$.paper.authors[0].name").value("test-name"));
    }

    @Test
    public void testPaperDetailsWrongUser() throws Exception {

        User wrongUser = mockUser();

        Research research = mockResearch(wrongUser);

        Paper paper = mockPaper();

        SavedPaper savedPaper = new SavedPaper(paper, research, new Comment("test-comment"), 3, SaveState.ADDED);

        research.addSavedPaper(savedPaper);

        given(researchService.getResearch(anyString(), eq(wrongUser))).willReturn(research);
        given(paperService.getPaper(eq("test-id"))).willReturn(paper);
        given(paperService.getSavedPaper(eq(wrongUser), eq(paper), eq(research))).willAnswer(invocation -> {
            throw new WrongUserException();});

        mvc.perform(get("/paper/test-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .requestAttr("user", wrongUser)
                        .with(jwt()))
                    .andExpect(status().isForbidden());
    }


    @Test
    public void testPaperDetailsInvalidId() throws Exception {

        User user = mockUser();

        Research research = mockResearch(user);

        given(researchService.getResearch(anyString(), eq(user))).willReturn(research);
        given(paperService.getPaper(eq("test-id"))).willAnswer(invocation -> {
            throw new NotInDataBaseException();
        });

        mvc.perform(get("/paper/test-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPaperAddTag() throws Exception {

        User user = mockUser();

        Research research = mockResearch(user);

        Paper paper = mockPaper();

        SavedPaper savedPaper = new SavedPaper(paper, research, new Comment("test-comment"), 3, SaveState.ADDED);

        research.addSavedPaper(savedPaper);

        Tag tag = mockTag(research);

        savedPaper.setTags(List.of(tag));

        given(researchService.getResearch(anyString(), eq(user))).willReturn(research);
        given(paperService.getPaper("test-id")).willReturn(paper);
        given(paperService.getSavedPaper(eq(user), eq(paper), eq(research))).willReturn(savedPaper);
        given(tagService.getTag(anyString(), eq(user))).willReturn(tag);
        doNothing().when(paperService).addTagToPaper(savedPaper, tag);

        /*
        mvc.perform(put("/paper/test-id/tag")
                        .contentType(MediaType.APPLICATION_JSON)

                        // TODO how to add request attributes and params for put


                .andExpect(status().isOk());*/
    }

    @Test
    public void testPaperAddTagAlreadyAdded() throws Exception {

        User user = mockUser();

        Research research = mockResearch(user);

        Paper paper = mockPaper();

        SavedPaper savedPaper = new SavedPaper(paper, research, new Comment("test-comment"), 3, SaveState.ADDED);

        research.addSavedPaper(savedPaper);

        Tag tag = mockTag(research);

        savedPaper.setTags(List.of(tag));

        given(researchService.getResearch(anyString(), eq(user))).willReturn(research);
        given(paperService.getPaper("test-id")).willReturn(paper);
        given(paperService.getSavedPaper(eq(user), eq(paper), eq(research))).willReturn(savedPaper);
        given(tagService.getTag(anyString(), eq(user))).willReturn(tag);
        doNothing().when(paperService).addTagToPaper(savedPaper, tag);

        mvc.perform(get("/paper/test-id/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .param("tagId", "test-tagId")
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isConflict());
    }



    private User mockUser() {
        return new User("test-user", "password", new UserCategory("0000FF", "test-category"));
    }

    private Research mockResearch(User user) {
        return new Research("test-title", new Comment("test-description"), ZonedDateTime.now(), user);
    }

    private Paper mockPaper() {
        return new Paper("test-id", "test-title", 2023, "test-abstract", 69, 420, "test-venue", "test-url", List.of(new Author("test-id", "test-name")));
    }

    private Tag mockTag(Research research) {
        return new Tag("0000FF", "test-tag", research);
    }

}