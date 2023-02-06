package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.config.RsaKeyProperties;
import edu.kit.iti.scale.lara.backend.controller.config.SecurityConfig;
import edu.kit.iti.scale.lara.backend.controller.config.WebConfig;
import edu.kit.iti.scale.lara.backend.controller.service.UserCategoryService;
import edu.kit.iti.scale.lara.backend.controller.service.UserService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
@Import(AdminController.class)
@ContextConfiguration(classes = {SecurityConfig.class, WebConfig.class})
@EnableConfigurationProperties(RsaKeyProperties.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @MockBean
    private UserService userService;

    @MockBean
    private UserCategoryService userCategoryService;

    @Test
    public void testListUsers() throws Exception {
        UserCategory userCategory = new UserCategory("0000FF", "test-category");
        User user = new User("test-user", "password", userCategory);
        List<User> users = Collections.singletonList(user);

        given(userService.getUsers()).willReturn(users);

        mvc.perform(
                get("/usermanagement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.wrap(Map.of("organizers", List.of())).toString())
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users[0].username").value("test-user"))
                .andExpect(jsonPath("$.users[0].userCategory.name").value("test-category"))
                .andExpect(jsonPath("$.users[0].userCategory.color").value("0000FF"));
    }

    @Test
    public void testCreateUser() throws Exception {
        mockGetCategoryByNameValid();

        mockCreateUser();

        JSONObject userObject = new JSONObject();
        userObject.put("username", "test-admin");
        userObject.put("password", "password");
        userObject.put("usercategory", "ADMIN");

        mvc.perform(post("/usermanagement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userObject.toString())
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test-admin"))
                .andExpect(jsonPath("$.userCategory.name").value("ADMIN"))
                .andExpect(jsonPath("$.userCategory.color").value("0000FF"));
    }

    @Test
    public void testCreateUserWithInvalidCategory() throws Exception {
        mockGetCategoryByNameInvalid();

        mockCreateUser();

        JSONObject userObject = new JSONObject();
        userObject.put("username", "test-admin");
        userObject.put("password", "password");
        userObject.put("usercategory", "INVALID");

        mvc.perform(post("/usermanagement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userObject.toString())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateUserWithEmptyPassword() throws Exception {
        mockGetCategoryByNameValid();

        mockCreateUser();

        JSONObject userObject = new JSONObject();
        userObject.put("username", "test-admin");
        userObject.put("password", "");
        userObject.put("usercategory", "ADMIN");

        mvc.perform(post("/usermanagement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userObject.toString())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateUserWithEmptyUsername() throws Exception {
        mockGetCategoryByNameValid();

        mockCreateUser();

        JSONObject userObject = new JSONObject();
        userObject.put("username", "");
        userObject.put("password", "password");
        userObject.put("usercategory", "ADMIN");

        mvc.perform(post("/usermanagement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userObject.toString())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockGetUserByIdValid();

        doNothing().when(userService).deleteUser(any(User.class));

        mvc.perform(delete("/usermanagement/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUserInvalid() throws Exception {
        mockGetUserByIdInvalid();

        doNothing().when(userService).deleteUser(any(User.class));

        mvc.perform(delete("/usermanagement/INVALID")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateUser() throws Exception {
        mockGetUserByIdValid();

        mockGetCategoryByNameValid();

        given(userService.updateUser(any(User.class), anyString(), anyString(), any(UserCategory.class))).willAnswer(invocation -> {
            String username = invocation.getArgument(1);
            String password = invocation.getArgument(2);
            UserCategory userCategory = invocation.getArgument(3);
            return new User(username, password, userCategory);
        });

        JSONObject userObject = new JSONObject();
        userObject.put("username", "new-username");
        userObject.put("password", "new-password");
        userObject.put("usercategory", "new-category");

        mvc.perform(patch("/usermanagement/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userObject.toString())
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("new-username"))
                .andExpect(jsonPath("$.userCategory.name").value("new-category"))
                .andExpect(jsonPath("$.userCategory.color").value("0000FF"));
    }

    @Test
    public void testUpdateUserInvalid() throws Exception {
        mockGetUserByIdInvalid();

        JSONObject userObject = new JSONObject();
        userObject.put("username", "new-username");
        userObject.put("password", "new-password");
        userObject.put("usercategory", "new-category");

        mvc.perform(patch("/usermanagement/INVALID")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userObject.toString())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateUserEmptyPassword() throws Exception {
        mockGetUserByIdValid();

        mockGetCategoryByNameValid();

        given(userService.updateUser(any(User.class), anyString(), anyString(), any(UserCategory.class))).willAnswer(invocation -> {
            String username = invocation.getArgument(1);
            String password = invocation.getArgument(2);
            UserCategory userCategory = invocation.getArgument(3);
            return new User(username, password, userCategory);
        });

        JSONObject userObject = new JSONObject();
        userObject.put("username", "new-username");
        userObject.put("password", "");
        userObject.put("usercategory", "new-category");

        mvc.perform(patch("/usermanagement/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userObject.toString())
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("new-username"))
                .andExpect(jsonPath("$.userCategory.name").value("new-category"))
                .andExpect(jsonPath("$.userCategory.color").value("0000FF"));
    }

    @Test
    public void testUpdateUserEmptyUsername() throws Exception {
        JSONObject userObject = new JSONObject();
        userObject.put("username", "");
        userObject.put("password", "new-password");
        userObject.put("usercategory", "new-category");

        mvc.perform(patch("/usermanagement/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userObject.toString())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateUserInvalidUserCategory() throws Exception {
        mockGetCategoryByNameInvalid();

        JSONObject userObject = new JSONObject();
        userObject.put("username", "new-username");
        userObject.put("password", "new-password");
        userObject.put("usercategory", "new-category");

        mvc.perform(patch("/usermanagement/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userObject.toString())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testListCategories() throws Exception {
        given(userCategoryService.getUserCategories()).willReturn(Arrays.asList(
                new UserCategory("0000FF", "test-category"),
                new UserCategory("FF0000", "test-category2")
        ));

        mvc.perform(get("/usermanagement/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories[0].name").value("test-category"))
                .andExpect(jsonPath("$.categories[0].color").value("0000FF"))
                .andExpect(jsonPath("$.categories[1].name").value("test-category2"))
                .andExpect(jsonPath("$.categories[1].color").value("FF0000"));
    }

    @Test
    public void testListCategoriesEmpty() throws Exception {
        given(userCategoryService.getUserCategories()).willReturn(Collections.emptyList());

        mvc.perform(get("/usermanagement/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories").isEmpty());
    }

    @Test
    public void testUpdateCategory() throws Exception {
        mockGetCategoryByNameValid();

        given(userCategoryService.updateCategory(any(UserCategory.class), anyString(), anyString())).willAnswer(invocation -> {
            String name = invocation.getArgument(1);
            String color = invocation.getArgument(2);
            return new UserCategory(color, name);
        });

        JSONObject categoryObject = new JSONObject();
        categoryObject.put("name", "new-category");
        categoryObject.put("color", "0000FF");

        mvc.perform(patch("/usermanagement/category/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryObject.toString())
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("new-category"))
                .andExpect(jsonPath("$.color").value("0000FF"));
    }

    @Test
    public void testUpdateCategoryInvalid() throws Exception {
        mockGetCategoryByNameInvalid();

        JSONObject categoryObject = new JSONObject();
        categoryObject.put("name", "new-category");
        categoryObject.put("color", "0000FF");

        mvc.perform(patch("/usermanagement/category/INVALID")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryObject.toString())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCategoryEmptyName() throws Exception {
        JSONObject categoryObject = new JSONObject();
        categoryObject.put("name", "");
        categoryObject.put("color", "0000FF");

        mvc.perform(patch("/usermanagement/category/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryObject.toString())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCategoryEmptyColor() throws Exception {
        JSONObject categoryObject = new JSONObject();
        categoryObject.put("name", "new-category");
        categoryObject.put("color", "");

        mvc.perform(patch("/usermanagement/category/id12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryObject.toString())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    private void mockCreateUser() {
        given(userService.createUser(anyString(), anyString(), any(UserCategory.class))).willAnswer(invocation -> {
            String username = invocation.getArgument(0);
            String password = invocation.getArgument(1);
            UserCategory userCategory = invocation.getArgument(2);
            password = encoder.encode(password);
            return new User(username, password, userCategory);
        });
    }

    private void mockGetCategoryByNameValid() throws NotInDataBaseException {
        given(userCategoryService.getUserCategoryByName(anyString())).willAnswer(invocation -> {
            String name = invocation.getArgument(0);
            return new UserCategory("0000FF", name);
        });
    }

    private void mockGetCategoryByNameInvalid() throws NotInDataBaseException {
        given(userCategoryService.getUserCategoryByName(anyString())).willAnswer(invocation -> {
            throw new NotInDataBaseException();
        });
    }

    private void mockGetUserByIdValid() throws NotInDataBaseException {
        given(userService.getUserById(anyString())).willAnswer(invocation ->
                new User("username", "password", new UserCategory("0000FF", "ADMIN")));
    }

    private void mockGetUserByIdInvalid() throws NotInDataBaseException {
        given(userService.getUserById(anyString())).willAnswer(invocation -> {
            throw new NotInDataBaseException();
        });
    }

}
