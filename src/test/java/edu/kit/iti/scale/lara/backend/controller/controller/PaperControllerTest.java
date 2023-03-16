package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.RecommendationMethod;
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

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        mvc.perform(put("/paper/test-id/tag")
                .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-research")
                        .param("tagId", "test-tagId")
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isConflict());
    }

    @Test
    public void testPaperAddTag() throws Exception {

        User user = mockUser();

        Research research = mockResearch(user);

        Paper paper = mockPaper();

        SavedPaper savedPaper = new SavedPaper(paper, research, new Comment("test-comment"), 3, SaveState.ADDED);

        research.addSavedPaper(savedPaper);

        Tag tag = mockTag(research);

        given(researchService.getResearch(anyString(), eq(user))).willReturn(research);
        given(paperService.getPaper("test-id")).willReturn(paper);
        given(paperService.getSavedPaper(eq(user), eq(paper), eq(research))).willReturn(savedPaper);
        given(tagService.getTag(anyString(), eq(user))).willReturn(tag);
        doNothing().when(paperService).addTagToPaper(savedPaper, tag);

        mvc.perform(put("/paper/test-id/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .param("tagId", "test-tagId")
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void testPaperAddTagWrongUser() throws Exception {

        User user = mockUser();

        Research research = mockResearch(user);

        Paper paper = mockPaper();

        SavedPaper savedPaper = new SavedPaper(paper, research, new Comment("test-comment"), 3, SaveState.ADDED);

        Tag tag = mockTag(research);

        given(researchService.getResearch(anyString(), eq(user))).willThrow(WrongUserException.class);
        given(paperService.getPaper("test-id")).willReturn(paper);
        given(paperService.getSavedPaper(eq(user), eq(paper), eq(research))).willReturn(savedPaper);
        given(tagService.getTag(anyString(), eq(user))).willReturn(tag);
        doNothing().when(paperService).addTagToPaper(savedPaper, tag);

        mvc.perform(put("/paper/test-id/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .param("tagId", "test-tagId")
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testPaperAddTagWrongPaper() throws Exception {

        User user = mockUser();

        Research research = mockResearch(user);

        given(researchService.getResearch(anyString(), eq(user))).willReturn(research);
        given(paperService.getPaper("test-id")).willThrow(NotInDataBaseException.class);

        mvc.perform(put("/paper/test-id/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .param("tagId", "test-tagId")
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }


        @Test
        public void testPaperTagRemove() throws Exception {
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
            doNothing().when(paperService).removeTagFromPaper(savedPaper, tag);

            mvc.perform(delete("/paper/test-id/tag")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("researchId", "test-researchId")
                            .param("tagId", "test-tagId")
                            .requestAttr("user", user)
                            .with(jwt()))
                    .andExpect(status().isOk());
        }


    @Test
    public void testPaperTagRemovePaperDoesntContainTag() throws Exception {
        User user = mockUser();

        Research research = mockResearch(user);

        Paper paper = mockPaper();

        SavedPaper savedPaper = new SavedPaper(paper, research, new Comment("test-comment"), 3, SaveState.ADDED);

        research.addSavedPaper(savedPaper);

        Tag tag = mockTag(research);

        given(researchService.getResearch(anyString(), eq(user))).willReturn(research);
        given(paperService.getPaper("test-id")).willReturn(paper);
        given(paperService.getSavedPaper(eq(user), eq(paper), eq(research))).willReturn(savedPaper);
        given(tagService.getTag(anyString(), eq(user))).willReturn(tag);
        doNothing().when(paperService).removeTagFromPaper(savedPaper, tag);

        mvc.perform(delete("/paper/test-id/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .param("tagId", "test-tagId")
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isConflict());
    }

    @Test
    public void testPaperTagRemoveWrongUser() throws Exception {
        User user = mockUser();

        given(researchService.getResearch(anyString(), eq(user))).willThrow(WrongUserException.class);

        mvc.perform(delete("/paper/test-id/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .param("tagId", "test-tagId")
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testPaperTagRemoveWrongPaper() throws Exception {
        User user = mockUser();

        Research research = mockResearch(user);

        given(researchService.getResearch(anyString(), eq(user))).willReturn(research);
        given(paperService.getPaper("test-id")).willThrow(NotInDataBaseException.class);

        mvc.perform(delete("/paper/test-id/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .param("tagId", "test-tagId")
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPaperComment() throws Exception {
        User user = mockUser();

        Research research = mockResearch(user);

        Paper paper = mockPaper();

        SavedPaper savedPaper = new SavedPaper(paper, research, new Comment("test-comment"), 3, SaveState.ADDED);

        research.addSavedPaper(savedPaper);

        given(researchService.getResearch(anyString(), eq(user))).willReturn(research);
        given(paperService.getPaper("test-id")).willReturn(paper);
        given(paperService.getSavedPaper(eq(user), eq(paper), eq(research))).willReturn(savedPaper);
        doNothing().when(paperService).commentPaper(savedPaper, "test-comment");

        mvc.perform(patch("/paper/test-id/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .content("{\"comment\":\"test-comment\"}")
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void testPaperCommentWrongUser() throws Exception {
        User user = mockUser();

        given(researchService.getResearch(anyString(), eq(user))).willThrow(WrongUserException.class);

        mvc.perform(patch("/paper/test-id/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .content("{\"comment\":\"test-comment\"}")
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testPaperCommentWrongPaper() throws Exception {
        User user = mockUser();

        Research research = mockResearch(user);

        given(researchService.getResearch(anyString(), eq(user))).willReturn(research);
        given(paperService.getPaper("test-id")).willThrow(NotInDataBaseException.class);

        mvc.perform(patch("/paper/test-id/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .content("{\"comment\":\"test-comment\"}")
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testPaperSaveState() throws Exception {
        User user = mockUser();

        Research research = mockResearch(user);

        Paper paper = mockPaper();

        SavedPaper savedPaper = new SavedPaper(paper, research, new Comment("test-comment"), 3, SaveState.ADDED);

        research.addSavedPaper(savedPaper);

        SaveState saveState = SaveState.ADDED;

        given(researchService.getResearch(anyString(), eq(user))).willReturn(research);
        given(paperService.getPaper("test-id")).willReturn(paper);
        given(paperService.getSavedPaper(eq(user), eq(paper), eq(research))).willReturn(savedPaper);
        doNothing().when(paperService).changeSaveState(savedPaper, saveState);

        mvc.perform(put("/paper/test-id/save-state")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("researchId", "test-researchId")
                            .param("save-state", String.valueOf(saveState))
                            .requestAttr("user", user)
                            .with(jwt()))
                    .andExpect(status().isOk());
    }


    @Test
    public void testPaperSaveStateWrongUser() throws Exception {
        User user = mockUser();

        SaveState saveState = SaveState.ADDED;

        given(researchService.getResearch(anyString(), eq(user))).willThrow(WrongUserException.class);

        mvc.perform(put("/paper/test-id/save-state")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .param("save-state", String.valueOf(saveState))
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }


    @Test
    public void testPaperSaveStateWrongPaper() throws Exception {
        User user = mockUser();

        Research research = mockResearch(user);

        SaveState saveState = SaveState.ADDED;

        given(researchService.getResearch(anyString(), eq(user))).willReturn(research);
        given(paperService.getPaper("test-id")).willThrow(NotInDataBaseException.class);

        mvc.perform(put("/paper/test-id/save-state")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .param("save-state", String.valueOf(saveState))
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testPaperRelevance() throws Exception {
        User user = mockUser();

        Research research = mockResearch(user);

        Paper paper = mockPaper();

        SavedPaper savedPaper = new SavedPaper(paper, research, new Comment("test-comment"), 3, SaveState.ADDED);

        research.addSavedPaper(savedPaper);

        int relevance = 69;

        given(researchService.getResearch(anyString(), eq(user))).willReturn(research);
        given(paperService.getPaper("test-id")).willReturn(paper);
        given(paperService.getSavedPaper(eq(user), eq(paper), eq(research))).willReturn(savedPaper);
        doNothing().when(paperService).setRelevanceOfPaper(savedPaper, relevance);

        mvc.perform(patch("/paper/test-id/relevance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .param("relevance", String.valueOf(relevance))
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void testPaperRelevanceWrongUser() throws Exception {
        User user = mockUser();

        given(researchService.getResearch(anyString(), eq(user))).willThrow(WrongUserException.class);

        mvc.perform(patch("/paper/test-id/relevance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .param("relevance", "69")
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testPaperRelevanceWrongPaper() throws Exception {
        User user = mockUser();

        Research research = mockResearch(user);

        int relevance = 69;

        given(researchService.getResearch(anyString(), eq(user))).willReturn(research);
        given(paperService.getPaper("test-id")).willThrow(NotInDataBaseException.class);

        mvc.perform(patch("/paper/test-id/relevance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("researchId", "test-researchId")
                        .param("relevance", String.valueOf(relevance))
                        .requestAttr("user", user)
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testPaperRecommendations() throws Exception {
        User user = mockUser();

        Research research = mockResearch(user);

        Paper paper = mockPaper();

        Paper paperOne = mockPaper();
        Paper paperTwo = mockPaper();

        List<Paper> papers = new ArrayList<>();

        papers.add(paperOne);
        papers.add(paperTwo);

        SavedPaper savedPaper = new SavedPaper(paper, research, new Comment("test-comment"), 3, SaveState.ADDED);

        research.addSavedPaper(savedPaper);

        given(paperService.getPaper(eq("test-id"), eq(true))).willReturn(paper);
        given(apiActionController.getRecommendations(eq(List.of(paper)), eq(List.of()))).willReturn(papers);
        given(apiActionController.getCitations(eq(paper))).willReturn(papers);
        given(apiActionController.getReferences(eq(paper))).willReturn(papers);

        List<RecommendationMethod> methods = List.of(RecommendationMethod.ALGORITHM, RecommendationMethod.REFERENCES, RecommendationMethod.CITATIONS);

        for (int i = 0; i < 3; i++) {
            mvc.perform(post("/paper/test-id/recommendations")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("method", String.valueOf(methods.get(i)))
                            .requestAttr("user", user)
                            .content("{\"organizers\":[]}")
                            .with(jwt()))
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void testPaperRecommendationsWrongPaper() throws Exception {
        User user = mockUser();

        given(paperService.getPaper("test-id", true)).willThrow(NotInDataBaseException.class);

        List<RecommendationMethod> methods = List.of(RecommendationMethod.ALGORITHM, RecommendationMethod.REFERENCES, RecommendationMethod.CITATIONS);

        for (int i = 0; i < 3; i++) {
            mvc.perform(post("/paper/test-id/recommendations")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("method", String.valueOf(methods.get(i)))
                            .requestAttr("user", user)
                            .content("{\"organizers\":[]}")
                            .with(jwt()))
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    public void testPaperRecommendationsIOError() throws Exception {
        User user = mockUser();

        Research research = mockResearch(user);

        Paper paper = mockPaper();

        SavedPaper savedPaper = new SavedPaper(paper, research, new Comment("test-comment"), 3, SaveState.ADDED);

        research.addSavedPaper(savedPaper);

        given(paperService.getPaper("test-id", true)).willReturn(paper);
        given(apiActionController.getRecommendations(List.of(paper), List.of())).willThrow(IOException.class);
        given(apiActionController.getCitations(paper)).willThrow(IOException.class);
        given(apiActionController.getReferences(paper)).willThrow(IOException.class);

        List<RecommendationMethod> methods = List.of(RecommendationMethod.ALGORITHM, RecommendationMethod.REFERENCES, RecommendationMethod.CITATIONS);

        for (int i = 0; i < 3; i++) {
            mvc.perform(post("/paper/test-id/recommendations")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("method", String.valueOf(methods.get(i)))
                            .requestAttr("user", user)
                            .content("{\"organizers\":[]}")
                            .with(jwt()))
                    .andExpect(status().isInternalServerError());
        }
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