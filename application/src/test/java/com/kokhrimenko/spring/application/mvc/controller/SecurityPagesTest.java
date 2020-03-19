package com.kokhrimenko.spring.application.mvc.controller;

import static com.kokhrimenko.spring.application.mvc.MvcConfig.HOME_PAGE;
import static com.kokhrimenko.spring.application.mvc.MvcConfig.LOGIN_PAGE;
import static com.kokhrimenko.spring.application.mvc.MvcConfig.ROOT_PAGE;
import static com.kokhrimenko.spring.application.mvc.MvcConfig.PROFILE_PAGE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.kokhrimenko.spring.application.Application;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = { Application.class })
@DisplayName("Spring Security tests")
public class SecurityPagesTest {

    @Autowired
    private MockMvc mvc;
    
    @Test
    @DisplayName("Request /login page by an authorized user.")
    void testLoginPageWithoutAuthorization() throws Exception {
        this.mvc.perform(get(LOGIN_PAGE))
                .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Request /home page by an un-authorized user.")
    void testHomePageWithoutAuthorization() throws Exception {
        this.mvc.perform(get(HOME_PAGE))
                .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser
    @DisplayName("Request / page by an un-authorized user.")
    void testRootPageWithoutAuthorization() throws Exception {
        this.mvc.perform(get(ROOT_PAGE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Request /profile page by an un-authorized user.")
    void testProfilePageWithoutAuthorization() throws Exception {
        this.mvc.perform(get(PROFILE_PAGE))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**" + LOGIN_PAGE));
    }

    @Test
    @WithMockUser
    @DisplayName("Request /profile page by an authorized user.")
    void testProfilePageWithAuthorization() throws Exception {
        this.mvc.perform(get(PROFILE_PAGE))
                .andExpect(status().isOk());
    }
}
