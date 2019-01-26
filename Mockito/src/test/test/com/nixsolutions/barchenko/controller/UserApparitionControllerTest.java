package com.nixsolutions.barchenko.controller;

import com.nixsolutions.barchenko.config.AppInitializer;
import com.nixsolutions.barchenko.config.WebConfig;
import com.nixsolutions.barchenko.config.WebSecurityConfig;
import com.nixsolutions.barchenko.entity.Role;
import com.nixsolutions.barchenko.entity.User;
import com.nixsolutions.barchenko.service.RoleService;
import com.nixsolutions.barchenko.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { WebConfig.class,
        WebSecurityConfig.class,
        AppInitializer.class })
public class UserApparitionControllerTest {

    private MockMvc mockMvc;

    @Mock
    Model model;

    @Mock
    User user;

    @Mock
    Role role;

    @InjectMocks
    UserApparitionController controller;

    @Mock
    UserService userService;

    @Mock
    RoleService roleService;

    @Before
    public void setUp() {
        model = mock(Model.class);
        user = mock(User.class);
        MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver).build();
    }

    @Test
    public void getUser() throws Exception {
        this.mockMvc.perform(get("/user"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void getCreate() throws Exception {
        this.mockMvc.perform(get("/create"))
                .andExpect(forwardedUrl("/WEB-INF/pages/create.jsp"))
                .andDo(print());
    }

    @Test
    public void getRegistration() throws Exception {
        this.mockMvc.perform(get("/registration"))
                .andExpect(forwardedUrl(
                        "/WEB-INF/pages/registration.jsp"))
                .andDo(print());
    }

    @Test
    public void getAdmin() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andExpect(forwardedUrl(
                        "/WEB-INF/pages/admin.jsp"))
                .andDo(print());
    }

    @Test
    public void getEdit() throws Exception {
        user.setLogin("test");
        when(userService.findByLogin(anyString())).thenReturn(user);
        this.mockMvc.perform(get("/edit/?editLogin=" + user.getLogin()))
                .andExpect(forwardedUrl("/WEB-INF/pages/edit.jsp"))
                .andDo(print());
        verify(roleService, times(1)).findAll();
        verify(userService, times(1))
                .findByLogin(anyString());
        verifyNoMoreInteractions(roleService);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void editUser() throws Exception {
        when(userService.findByLogin(any())).thenReturn(user);
        mockMvc.perform(post("/edit")
                .param("login", "test")
                .param("password", "test")
                .param("passwordAgain", "test")
                .param("role", "2")
        ).andExpect(forwardedUrl("/WEB-INF/pages/admin.jsp"))
                .andDo(print());
        verify(userService, times(1)).update(any());
        verify(roleService, times(1))
                .findById(anyLong());
        verify(userService, times(1))
                .findByLogin(anyString());
        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(roleService);
    }

    @Test
    public void getDelete() throws Exception {
        role = new Role(2L,"USER");
        user = new User("log", "pass",
                "e@mail.ru", "testDelete",
                "delete1",
                Date.valueOf("1999-11-11"), role);

        this.mockMvc.perform(get("/delete/?deleteLogin=" +
                user.getLogin())).andExpect(
                redirectedUrl("/admin")).andDo(print());
        verify(userService, times(1)).remove(any());
        verify(userService, times(1))
                .findByLogin(anyString());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void createUser() throws Exception {
        mockMvc.perform(post("/create")
                .param("login", "createTest")
                .param("password", "password")
                .param("passwordAgain", "password")
                .param("email", "e@mail.ru")
                .param("firstName", "fn")
                .param("lastName", "ln")
                .param("birthday", "2000-10-10")
                .param("role", "2")
        ).andExpect(forwardedUrl("/WEB-INF/pages/admin.jsp"))
                .andDo(print());
        verify(userService, times(1)).create(any());
        verify(roleService, times(1)).findById(anyLong());
        verify(userService, times(2)).findAll();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void loginExistCreateTest() throws Exception {
        List<User> users = new ArrayList<>();
        user = new User();
        user.setLogin("test1");
        users.add(user);
        when(userService.findAll()).thenReturn(users);
        mockMvc.perform(post("/create")
                .param("login", "test1")
                .param("password", "test")
                .param("passwordAgain", "test")
                .param("role", "2"))
                .andExpect(forwardedUrl("/WEB-INF/pages/create.jsp"))
                .andDo(print());
        verify(userService, never()).create(any());
        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void loginExistRegistrationTest() throws Exception {
        List<User> users = new ArrayList<>();
        user = new User();
        user.setLogin("test2");
        user.setRole(new Role(2L, "USER"));
        users.add(user);
        when(userService.findAll()).thenReturn(users);
        mockMvc.perform(post("/registration")
                .param("login", "test2")
                .param("password", "test")
                .param("passwordAgain", "test")
                .param("role", "2")
                .param("g-recaptcha-response", "response")
        ).andExpect(forwardedUrl("/WEB-INF/pages/registration.jsp"))
                .andDo(print());
        verify(userService, never()).create(any());
        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void passwordNotEqualsCreateTest() throws Exception {
        this.mockMvc.perform(post("/create")
                .param("password","pass1")
                .param("passwordAgain","pass2"))
                .andExpect(forwardedUrl("/WEB-INF/pages/create.jsp"))
                .andExpect(model().attribute(
                        "error", "Passwords are not the same"))
                .andDo(print());
        verify(userService, never()).create(any());
        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void passwordNotEqualsRegistrationTest() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("login", "test")
                .param("password", "test1")
                .param("passwordAgain", "test2")
                .param("role", "2")
                .param("g-recaptcha-response", "response")
        ).andExpect(forwardedUrl("/WEB-INF/pages/registration.jsp"))
                .andExpect(model().attribute(
                        "error", "Passwords are not the same"))
                .andDo(print());
        verify(userService, never()).create(any());
        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void passwordNotEqualsEditTest() throws Exception {
        when(userService.findByLogin(any())).thenReturn(user);
        this.mockMvc.perform(post("/edit")
                .param("login", "test")
                .param("password","pass1")
                .param("passwordAgain","pass2")
        ).andExpect(forwardedUrl("/WEB-INF/pages/edit.jsp"))
                .andExpect(model().attribute(
                        "error", "Passwords are not the same"))
                .andDo(print());
        verify(userService, never()).update(any());
        verify(userService, times(1)).findByLogin(anyString());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void captchaTestMethodShouldNotCreateUser()
            throws Exception {
        User user = new User("test", "test",
                "email@mail", "Test", "Test",
                Date.valueOf("2000-10-10"), new Role(2L, "USER"));
        mockMvc.perform(post("/registration")
                .flashAttr("user", user)
                .param("passwordAgain", "test")
                .param("g-recaptcha-response", "response"))
        .andExpect(model().attribute("error", "Captcha is not success"));

        verify(userService, never()).create(anyObject());
        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }


}