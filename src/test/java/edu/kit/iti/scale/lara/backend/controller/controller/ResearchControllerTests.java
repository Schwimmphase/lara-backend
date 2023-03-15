package edu.kit.iti.scale.lara.backend.controller.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kit.iti.scale.lara.backend.controller.RecommendationMethod;
import edu.kit.iti.scale.lara.backend.controller.config.RsaKeyProperties;
import edu.kit.iti.scale.lara.backend.controller.config.SecurityConfig;
import edu.kit.iti.scale.lara.backend.controller.config.WebConfig;
import edu.kit.iti.scale.lara.backend.controller.service.*;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.user.User;
import org.checkerframework.common.value.qual.StaticallyExecutable;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static edu.kit.iti.scale.lara.backend.TestObjects.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResearchController.class)
@Import(ResearchController.class)
@ContextConfiguration(classes = {SecurityConfig.class, WebConfig.class})
@EnableConfigurationProperties(RsaKeyProperties.class)
public class ResearchControllerTests {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ResearchService researchService;

    @MockBean
    private PaperService paperService;

    @MockBean
    private TagService tagService;

    @Test
    public void testCreateResearch() throws Exception {
        mockCreateResearch();

        JSONObject request = new JSONObject();
        request.put("title", "title");
        request.put("description", "description");

        mvc.perform(post("/research")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.comment").value("description"));
    }

    @Test
    public void testCreateResearchEmptyTitle() throws Exception {
        mockCreateResearch();

        JSONObject request = new JSONObject();
        request.put("title", "");
        request.put("description", "description");

        mvc.perform(post("/research")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testListResearches() throws Exception {

        given(researchService.getResearches(any(User.class))).willAnswer(invocation -> {
            return List.of(research());
        });

        mvc.perform(get("/research")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.researches[0].id").value("id12345"))
                .andExpect(jsonPath("$.researches[0].title").value("test-research"))
                .andExpect(jsonPath("$.researches[0].comment").value("description"));
    }

    @Test
    public void testUpdateResearch() throws Exception {
        mockGetResearch();
        mockUpdateResearch();

        JSONObject request = new JSONObject();
        request.put("title", "new-title");
        request.put("description", "new-description");

        mvc.perform(patch("/research/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("new-title"))
                .andExpect(jsonPath("$.comment").value("new-description"));
    }

    @Test
    public void testDeleteResearch() throws Exception {
        mockGetResearch();
        mockDeleteResearch();

        mvc.perform(delete("/research/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect((status().isOk()));
    }

    @Test
    public void testSavePaper() throws Exception {
        mockGetResearch();
        mockGetPaper();
        mockCreateSavedPaper();

        mvc.perform(put("/research/id12345/paper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("paperId", "12345")
                        .param("state", SaveState.ADDED.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePaper() throws Exception {
        mockGetResearch();
        mockGetPaper();
        mockGetSavedPaper();

        mvc.perform(delete("/research/id12345/paper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .param("paperId", "12345")
                        .with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void testResearchTags() throws Exception {
        mockGetResearch();
        mockGetTags();

        mvc.perform(get("/research/id12345/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tags[0].id").value("id12345"))
                .andExpect(jsonPath("$.tags[0].name").value("test-tag"))
                .andExpect(jsonPath("$.tags[0].color").value("test-color"));
    }

    @Test
    public void testResearchPapers() throws Exception {
        mockGetResearch();
        mockUserOpenedResearch();
        mockGetSavedPapers();

        mvc.perform(post("/research/id12345/papers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .content(JSONObject.wrap(Map.of("organizers", List.of())).toString())
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.papers[0].relevance").value(0))
                .andExpect(jsonPath("$.papers[0].saveState").value(SaveState.ADDED.toString()))
                .andExpect(jsonPath("$.papers[0].paper.paperId").value(paper().getPaperId()))
                .andExpect(jsonPath("$.papers[0].research.id").value(research().getId()));
    }

    @Test
    public void testResearchRecommendations() throws Exception {
        mockGetResearch();
        mockGetAddedPapers();
        mockGetRecommendations();
        mockGetCitations();
        mockGetReferences();

        mvc.perform(post("/research/id12345/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .param("method", RecommendationMethod.ALGORITHM.toString())
                        .content(JSONObject.wrap(Map.of("organizers", List.of())).toString())
                        .with(jwt()))
                .andExpect(status().isOk());
    }

    private void mockCreateResearch() {
        given(researchService.createResearch(any(User.class), anyString(), anyString())).willAnswer(invocation -> {
            User user = invocation.getArgument(0);
            String title = invocation.getArgument(1);
            String description = invocation.getArgument(2);

            return research("id12345", title, description, user);
        });
    }

    private void mockGetResearch() throws NotInDataBaseException, WrongUserException {
        given(researchService.getResearch(anyString(), any(User.class))).willAnswer(invocation -> research());
    }

    private void mockUpdateResearch() {
        given(researchService.updateResearch(any(Research.class), anyString(), anyString())).willAnswer(invocation -> {
            Research research = invocation.getArgument(0);
            research.setTitle(invocation.getArgument(1));
            research.setDescription(new Comment(invocation.getArgument(2)));
            return research;
        });
    }

    private void mockDeleteResearch() {
        doNothing().when(researchService).deleteResearch(any(Research.class), any(User.class));
    }

    private void mockGetPaper() throws NotInDataBaseException {
        given(paperService.getPaper(anyString())).willAnswer(invocation -> paper());
    }

    private void mockCreateSavedPaper() throws IOException {
        given(paperService.createSavedPaper(any(Research.class), any(Paper.class), any(SaveState.class))).willAnswer(invocation -> savedPaper());
    }

    private void mockGetSavedPaper() throws NotInDataBaseException, WrongUserException {
        given(paperService.getSavedPaper(any(User.class), any(Paper.class), any(Research.class))).willAnswer(invocation -> savedPaper());
    }

    private void mockGetSavedPapers() throws WrongUserException {
        given(paperService.getSavedPapers(any(Research.class), any(User.class))).will(invocation -> List.of(savedPaper()));
    }

    private void mockGetTags() {
        given(tagService.getTags(any(Research.class))).will(invocation -> List.of(tag()));
    }

    private void mockUserOpenedResearch() throws IOException {
        doNothing().when(userService).userOpenedResearch(any(User.class), any(Research.class));
    }

    private void mockGetAddedPapers() throws WrongUserException {
        given(paperService.getAddedPapers(any(Research.class), any(User.class))).willAnswer(invocation -> List.of(paper()));
    }

    private void mockGetRecommendations() throws IOException {
        given(researchService.getRecommendations(any(Research.class))).willAnswer(invocation -> List.of(paper()));
    }

    private void mockGetCitations() throws IOException {
        given(researchService.getCitations(any(Research.class), any())).willAnswer(invocation -> List.of(paper()));
    }

    private void mockGetReferences() throws IOException {
        given(researchService.getCitations(any(Research.class), any())).willAnswer(invocation -> List.of(paper()));
    }
}

