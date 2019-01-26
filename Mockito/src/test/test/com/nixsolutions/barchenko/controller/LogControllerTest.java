package com.nixsolutions.barchenko.controller;

import com.nixsolutions.barchenko.config.AppInitializer;
import com.nixsolutions.barchenko.config.WebConfig;
import com.nixsolutions.barchenko.config.WebSecurityConfig;
import com.nixsolutions.barchenko.entity.Role;
import com.nixsolutions.barchenko.entity.User;
import com.nixsolutions.barchenko.service.RoleService;
import com.nixsolutions.barchenko.service.UserService;
import com.nixsolutions.barchenko.utils.Fields;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.sql.Date;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { WebConfig.class,
        WebSecurityConfig.class,
        AppInitializer.class })
public class LogControllerTest {

    @Mock
    User user;

    @Mock
    Role role;

    private MockMvc mockMvc;

    @Mock
    Principal mockPrincipal;

    @Mock
    UserService userService;

    @InjectMocks
    LogController controller;


//    SessionFactory sessionFactory;
//
//    HttpSession httpSession = mock(HttpSession.class);
//
//    Authentication authentication;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver resourceViewResolver =
                new InternalResourceViewResolver();
        resourceViewResolver.setPrefix("/WEB-INF/pages/");
        resourceViewResolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(resourceViewResolver).build();
    }

    @Test
    public void loginPage() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/login.jsp"))
                .andDo(print());
    }

    @Test
    public void logout() throws Exception {
        this.mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andDo(print());
    }

    @Test
    public void errorPage() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        this.mockMvc.perform(
                get("/error").principal(mockPrincipal))
                .andExpect(redirectedUrl("/error")).andDo(print());
    }

    @Test
    public void loginAdmin() throws Exception {
        role = new Role(1L, "ADMIN");
        user = new User();
        user.setRole(role);
        when(userService.findByLogin(anyString())).thenReturn(user);
        mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("admin");
        this.mockMvc.perform(get("/enter")
                .principal(mockPrincipal))
                .andExpect(redirectedUrl("/admin"))
                .andDo(print());
        verify(userService, times(1))
                .findByLogin(anyString());
        verifyNoMoreInteractions(userService);
    }
    @Test
    public void loginUser() throws Exception {
        role = new Role(2L, "USER");
        user = new User();
        user.setRole(role);
        when(userService.findByLogin(anyString())).thenReturn(user);
        mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("USER");
        this.mockMvc.perform(get("/enter").
                principal(mockPrincipal))
                .andExpect(redirectedUrl("/user"))
                .andDo(print());
        verify(userService, times(1))
                .findByLogin(anyString());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void notSupport() throws Exception {
        mockMvc.perform(post("/enter")
                .param("login", "bjpejbpjebap"))
                .andExpect(status().is4xxClientError()).andDo(print());
    }
}