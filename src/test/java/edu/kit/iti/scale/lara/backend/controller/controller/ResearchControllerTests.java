package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.RecommendationMethod;
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
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.user.User;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static edu.kit.iti.scale.lara.backend.TestObjects.paper;
import static edu.kit.iti.scale.lara.backend.TestObjects.research;
import static edu.kit.iti.scale.lara.backend.TestObjects.savedPaper;
import static edu.kit.iti.scale.lara.backend.TestObjects.tag;
import static edu.kit.iti.scale.lara.backend.TestObjects.user;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    public void testUpdateResearchEmptyTitle() throws Exception {
        mockGetResearch();
        mockUpdateResearch();

        JSONObject request = new JSONObject();
        request.put("title", "");
        request.put("description", "new-description");

        mvc.perform(patch("/research/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateResearchLongTitle() throws Exception {
        mockGetResearch();
        mockUpdateResearch();

        JSONObject request = new JSONObject();
        request.put("title", "ThisTitleIsLongerThan25Symbols");
        request.put("description", "new-description");

        mvc.perform(patch("/research/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateResearchInvalidResearchId() throws Exception {

        JSONObject request = new JSONObject();
        request.put("title", "new-title");
        request.put("description", "new-description");

        given(researchService.getResearch(eq("invalid-id"), any(User.class))).willThrow(NotInDataBaseException.class);

        mvc.perform(patch("/research/invalid-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdateResearchWrongUser() throws Exception {

        JSONObject request = new JSONObject();
        request.put("title", "new-title");
        request.put("description", "new-description");

        given(researchService.getResearch(eq("id12345"), any())).willThrow(WrongUserException.class);

        mvc.perform(patch("/research/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isForbidden());
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
    public void testDeleteResearchInvalidId() throws Exception {

        given(researchService.getResearch(eq("invalid-id"), any(User.class))).willThrow(NotInDataBaseException.class);

        mvc.perform(delete("/research/invalid-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect((status().isForbidden()));
    }

    @Test
    public void testDeleteResearchWrongUser() throws Exception {

        given(researchService.getResearch(anyString(), any(User.class))).willThrow(WrongUserException.class);

        mvc.perform(delete("/research/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect((status().isForbidden()));
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
    public void testSavePaperInvalidResearchId() throws Exception {

        given(researchService.getResearch(eq("invalid-id"), any(User.class))).willThrow(NotInDataBaseException.class);

        mvc.perform(put("/research/invalid-id/paper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("paperId", "12345")
                        .param("state", SaveState.ADDED.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testSavePaperInvalidPaperId() throws Exception {
        mockGetResearch();

        given(paperService.getPaper(eq("invalid-id"), eq(true))).willThrow(NotInDataBaseException.class);

        mvc.perform(put("/research/id12345/paper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("paperId", "invalid-id")
                        .param("state", SaveState.ADDED.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testSavePaperWrongUser() throws Exception {

        given(researchService.getResearch(eq("id12345"), any(User.class))).willThrow(WrongUserException.class);

        mvc.perform(put("/research/id12345/paper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("paperId", "test-id")
                        .param("state", SaveState.ADDED.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testSavePaperIOError() throws Exception {
        mockGetResearch();
        mockGetPaper();

        given(paperService.createSavedPaper(any(Research.class), any(Paper.class), any(SaveState.class))).willThrow(IOException.class);

        mvc.perform(put("/research/id12345/paper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("paperId", "test-id")
                        .param("state", SaveState.ADDED.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isInternalServerError());
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
    public void testDeletePaperInvalidResearchId() throws Exception {
        given(researchService.getResearch(any(String.class), any(User.class))).willThrow(NotInDataBaseException.class);

        mvc.perform(delete("/research/id12345/paper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .param("paperId", "12345")
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeletePaperInvalidPaperId() throws Exception {
        given(paperService.getPaper(any(String.class))).willThrow(NotInDataBaseException.class);

        mvc.perform(delete("/research/id12345/paper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .param("paperId", "12345")
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeletePaperWrongUser() throws Exception {
        given(researchService.getResearch(any(String.class), any(User.class))).willThrow(WrongUserException.class);

        mvc.perform(delete("/research/id12345/paper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .param("paperId", "12345")
                        .with(jwt()))
                .andExpect(status().isForbidden());
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
    public void testResearchTagsInvalidResearchId() throws Exception {
        given(researchService.getResearch(any(String.class), any(User.class))).willThrow(NotInDataBaseException.class);

        mvc.perform(get("/research/id12345/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testResearchTagsWrongUser() throws Exception {
        given(researchService.getResearch(any(String.class), any(User.class))).willThrow(WrongUserException.class);


        mvc.perform(get("/research/id12345/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isForbidden());
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
    public void testResearchPapersInvalidResearchId() throws Exception {
        given(researchService.getResearch(any(String.class), any(User.class))).willThrow(NotInDataBaseException.class);

        mvc.perform(post("/research/id12345/papers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .content(JSONObject.wrap(Map.of("organizers", List.of())).toString())
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testResearchPapersWrongUser() throws Exception {
        given(researchService.getResearch(any(String.class), any(User.class))).willThrow(WrongUserException.class);


        mvc.perform(post("/research/id12345/papers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .content(JSONObject.wrap(Map.of("organizers", List.of())).toString())
                        .with(jwt()))
                .andExpect(status().isForbidden());
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

    @Test
    public void testResearchRecommendationsInvalidResearchId() throws Exception {
        given(researchService.getResearch(any(String.class), any(User.class))).willThrow(NotInDataBaseException.class);

        mvc.perform(post("/research/id12345/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .param("method", RecommendationMethod.ALGORITHM.toString())
                        .content(JSONObject.wrap(Map.of("organizers", List.of())).toString())
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testResearchRecommendationsWrongUser() throws Exception {
        given(researchService.getResearch(any(String.class), any(User.class))).willThrow(WrongUserException.class);

        mvc.perform(post("/research/id12345/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .param("method", RecommendationMethod.ALGORITHM.toString())
                        .content(JSONObject.wrap(Map.of("organizers", List.of())).toString())
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testResearchRecommendationsIOError() throws Exception {
        mockGetResearch();
        mockGetAddedPapers();
        mockGetCitations();
        mockGetReferences();

        given(researchService.getRecommendations(any(Research.class))).willThrow(IOException.class);

        mvc.perform(post("/research/id12345/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .param("method", RecommendationMethod.ALGORITHM.toString())
                        .content(JSONObject.wrap(Map.of("organizers", List.of())).toString())
                        .with(jwt()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testResearchSearch() throws Exception {
        mockSearchByKeyword();

        mvc.perform(post("/research/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("query", "test-query")
                .content(JSONObject.wrap(Map.of("organizers", List.of())).toString())
                .requestAttr("user", user())
                .with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void testResearchSearchIOError() throws Exception {
        given(researchService.searchByKeyword(any(String.class), any())).willThrow(IOException.class);

        mvc.perform(post("/research/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("query", "test-query")
                        .content(JSONObject.wrap(Map.of("organizers", List.of())).toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isInternalServerError());
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
        given(paperService.getPaper(anyString(), anyBoolean())).willAnswer(invocation -> paper());
    }

    private void mockGetPaperLookInApi() throws NotInDataBaseException {
        given(paperService.getPaper(anyString(), anyBoolean())).willAnswer(invocation -> paper());
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

    private void mockGetCitations() {
        given(researchService.getCitations(any(Research.class), any())).willAnswer(invocation -> List.of(paper()));
    }

    private void mockGetReferences() {
        given(researchService.getCitations(any(Research.class), any())).willAnswer(invocation -> List.of(paper()));
    }

    private void mockSearchByKeyword() throws IOException {
        given(researchService.searchByKeyword(any(String.class), any(Research.class))).willAnswer(invocation -> List.of(paper()));
    }
}

