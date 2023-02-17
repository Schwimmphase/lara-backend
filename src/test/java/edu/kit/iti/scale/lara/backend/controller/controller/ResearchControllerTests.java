package edu.kit.iti.scale.lara.backend.controller.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kit.iti.scale.lara.backend.controller.config.RsaKeyProperties;
import edu.kit.iti.scale.lara.backend.controller.config.SecurityConfig;
import edu.kit.iti.scale.lara.backend.controller.config.WebConfig;
import edu.kit.iti.scale.lara.backend.controller.service.*;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
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

import static edu.kit.iti.scale.lara.backend.TestObjects.research;
import static edu.kit.iti.scale.lara.backend.TestObjects.user;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

import java.util.List;

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

    private void mockCreateResearch() {
        given(researchService.createResearch(any(User.class), anyString(), anyString())).willAnswer(invocation -> {
            User user = invocation.getArgument(0);
            String title = invocation.getArgument(1);
            String description = invocation.getArgument(2);

            return research("id12345", title, description, user);
        });
    }

    private void mockGetResearch() throws NotInDataBaseException, WrongUserException {
        given(researchService.getResearch(anyString(), any(User.class))).willAnswer(invocation -> {
            return research();
        });
    }

    private void mockUpdateResearch() {
        given(researchService.updateResearch(any(Research.class), anyString(), anyString())).willAnswer(invocation -> {
            Research research = invocation.getArgument(0);
            research.setTitle(invocation.getArgument(1));
            research.setDescription(new Comment(invocation.getArgument(2)));
            return research;
        });
    }
}

