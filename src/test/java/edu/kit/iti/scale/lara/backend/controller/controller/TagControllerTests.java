package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.config.RsaKeyProperties;
import edu.kit.iti.scale.lara.backend.controller.config.SecurityConfig;
import edu.kit.iti.scale.lara.backend.controller.config.WebConfig;
import edu.kit.iti.scale.lara.backend.controller.service.ResearchService;
import edu.kit.iti.scale.lara.backend.controller.service.TagService;
import edu.kit.iti.scale.lara.backend.controller.service.UserCategoryService;
import edu.kit.iti.scale.lara.backend.controller.service.UserService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
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

import java.time.ZonedDateTime;

import static edu.kit.iti.scale.lara.backend.TestObjects.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TagController.class)
@Import(TagController.class)
@ContextConfiguration(classes = {SecurityConfig.class, WebConfig.class})
@EnableConfigurationProperties(RsaKeyProperties.class)
public class TagControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    TagService tagService;

    @MockBean
    ResearchService researchService;

    @MockBean
    UserService userService;

    @MockBean
    UserCategoryService userCategoryService;

    @Test
    public void testCreateTag() throws Exception {
        mockGetResearch();
        mockCreateTag();

        JSONObject request = new JSONObject();
        request.put("name", "test-name");
        request.put("color", "test-color");


        mvc.perform(post("/tag?researchId=12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(jsonPath("$.name").value("test-name"))
                .andExpect(jsonPath("$.color").value("test-color"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateTagEmptyName() throws Exception {
        mockGetResearch();
        mockCreateTag();

        JSONObject request = new JSONObject();
        request.put("name", "");
        request.put("color", "test-color");


        mvc.perform(post("/tag?researchId=12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateTagWrongResearch() throws Exception {
        mockGetResearchWrongResearch();
        mockCreateTag();

        JSONObject request = new JSONObject();
        request.put("name", "test-name");
        request.put("color", "test-color");


        mvc.perform(post("/tag?researchId=12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    private void mockGetResearchWrongResearch() throws NotInDataBaseException, WrongUserException {
        given(researchService.getResearch(anyString(), any(User.class))).willAnswer(invocation -> {
            throw new NotInDataBaseException();
        });
    }

    @Test
    public void testUpdateTag() throws Exception {
        mockGetTag();
        mockUpdateTag();

        JSONObject request = new JSONObject();
        request.put("name", "new-name");
        request.put("color", "new-color");

        mvc.perform(patch("/tag/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("new-name"))
                .andExpect(jsonPath("$.color").value("new-color"));
    }

    @Test
    public void testUpdateTagEmptyName() throws Exception {
        mockGetTag();
        mockUpdateTag();

        JSONObject request = new JSONObject();
        request.put("name", " ");
        request.put("color", "new-color");

        mvc.perform(patch("/tag/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateTagWrongUser() throws Exception {
        mockGetTagWrongUser();

        JSONObject request = new JSONObject();
        request.put("name", "new-name");
        request.put("color", "new-color");

        mvc.perform(patch("/tag/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    private void mockGetTagWrongUser() throws NotInDataBaseException, WrongUserException {
        given(tagService.getTag(anyString(), any(User.class))).willAnswer(invocation -> {
            throw new WrongUserException();
        });
    }

    @Test
    public void testDeleteTag() throws Exception {
        mockDeleteTag();
        mockGetTag();

        mvc.perform(delete("/tag/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTagWrongUser() throws Exception {
        mockDeleteTag();
        mockGetTagWrongUser();

        mvc.perform(delete("/tag/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    private void mockGetResearch() throws NotInDataBaseException, WrongUserException {
        given(researchService.getResearch(anyString(), any(User.class))).willAnswer(invocation -> research());
    }

    private void mockCreateTag() {

        given(tagService.createTag(anyString(), anyString(), any(Research.class))).willAnswer(invocation -> {
            return new Tag(invocation.getArgument(0), invocation.getArgument(1), invocation.getArgument(2));
        });
    }

    private void mockGetTag() throws NotInDataBaseException, WrongUserException {
        given(tagService.getTag(anyString(), any(User.class))).willAnswer(invocation -> tag());
    }

    private void mockUpdateTag() {
        given(tagService.updateTag(any(Tag.class), anyString(), anyString())).willAnswer(invocation -> {
            Tag tag = invocation.getArgument(0);
            tag.setName(invocation.getArgument(1));
            tag.setColor(invocation.getArgument(2));
            return tag;
        });
    }

    private void mockDeleteTag() {
        doNothing().when(tagService).deleteTag(any(Tag.class), any(Research.class));
    }

}