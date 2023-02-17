package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.config.RsaKeyProperties;
import edu.kit.iti.scale.lara.backend.controller.config.SecurityConfig;
import edu.kit.iti.scale.lara.backend.controller.config.WebConfig;
import edu.kit.iti.scale.lara.backend.controller.service.*;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.user.User;
import net.minidev.json.JSONArray;
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

import static edu.kit.iti.scale.lara.backend.TestObjects.user;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;


import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BibtexExportController.class)
@Import(BibtexExportController.class)
@ContextConfiguration(classes = {SecurityConfig.class, WebConfig.class})
@EnableConfigurationProperties(RsaKeyProperties.class)
public class BibTexExportControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BibtexConverterService bibtexConverterService;

    @MockBean
    private ResearchService researchService;

    @MockBean
    private PaperService paperService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserCategoryService userCategoryService;

    @Test
    public void testExportResearch() throws Exception {
        mockExportPapers();
        mockGetResearch();
        mockGetPapers();

        JSONObject organizer = new JSONObject();
        organizer.put("organizers", new JSONArray());


        mvc.perform(post("/export/research/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(organizer.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.export").value("@article{1}, title={title1}, author={author1}, year={2023}, journal={journal1}}\n" +
                        "\n" +
                        "@article{2}, title={title2}, author={author2}, year={2023}, journal={journal2}}\n" +
                        "\n" +
                        "@article{3}, title={title3}, author={author3}, year={2023}, journal={journal3}\n"));
    }

    @Test
    public void testExportPaper() throws Exception {
        mockExportSinglePaper();
        mockGetResearch();
        mockGetPaper();


        JSONObject organizer = new JSONObject();
        organizer.put("organizers", new JSONArray());


        mvc.perform(get("/export/paper/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(organizer.toString())
                        .requestAttr("user", user())
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.export").value("@article{1}, title={title1}, author={author1}, year={2023}, journal={journal1}}\n\n"));
    }


    private void mockExportPapers() {
        given(bibtexConverterService.export(anyList())).willAnswer(invocation -> "@article{1}, title={title1}, author={author1}, year={2023}, journal={journal1}}\n" +
                "\n" +
                "@article{2}, title={title2}, author={author2}, year={2023}, journal={journal2}}\n" +
                "\n" +
                "@article{3}, title={title3}, author={author3}, year={2023}, journal={journal3}\n");
    }

    private void mockExportSinglePaper() {
        given(bibtexConverterService.export(anyList())).willAnswer(invocation -> "@article{1}, title={title1}, author={author1}, year={2023}, journal={journal1}}\n\n");
    }


    private void mockGetResearch() throws NotInDataBaseException, WrongUserException {
        given(researchService.getResearch(anyString(), any(User.class))).willAnswer(invocation -> new Research("new-research", new Comment(), ZonedDateTime.now(), new User()));
    }

    private void mockGetPapers() throws WrongUserException {
        given(paperService.getSavedPapers(any(Research.class), any(User.class))).willAnswer(invocation -> (List<Paper>) new ArrayList<Paper>());
    }

    private void mockGetPaper() throws NotInDataBaseException {
        given(paperService.getPaper(anyString())).willAnswer(invocation -> new Paper());
    }
}
