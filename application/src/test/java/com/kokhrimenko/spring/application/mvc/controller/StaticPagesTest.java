package com.kokhrimenko.spring.application.mvc.controller;

import static com.kokhrimenko.spring.application.mvc.MvcConfig.HOME_PAGE;
import static com.kokhrimenko.spring.application.mvc.MvcConfig.PROFILE_PAGE;
import static com.kokhrimenko.spring.application.mvc.MvcConfig.ROOT_PAGE;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.kokhrimenko.spring.application.Application;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = { Application.class })
@TestPropertySource("classpath:messages.properties")
@DisplayName("HTML Errors controller test")
public class StaticPagesTest {

    @Autowired
    private MockMvc mvc;

    @Value("${pages.home.title}")
    private String homePageTitle;

    @Value("${pages.profile.title}")
    private String profilePageTitle;

    @Test
    @DisplayName("Test error page 404.")
    void test404Page() throws Exception {
        this.mvc.perform(get("/" + UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test home page.")
    void testHomePage() throws Exception {
        this.mvc.perform(get(HOME_PAGE))
                .andExpect(status().isOk())
                .andExpect(content().string(containsStringIgnoringCase(homePageTitle)));
    }

    @Test
    @DisplayName("Test profile page.")
    void testProfilePage() throws Exception {
        this.mvc.perform(get(PROFILE_PAGE))
                .andExpect(status().isOk())
                .andExpect(content().string(containsStringIgnoringCase(profilePageTitle)));
    }

    @Test
    @DisplayName("Test root page. Should be the same as home ones")
    void testRootPage() throws Exception {
        this.mvc.perform(get(ROOT_PAGE))
                .andExpect(status().isOk())
                .andExpect(content().string(containsStringIgnoringCase(homePageTitle)));
    }

    @Test
    @DisplayName("Test profile page with POST request. Should be NOT_FOUNT response code")
    void testProfilePageWithPostRequest() throws Exception {
        this.mvc.perform(post(PROFILE_PAGE))
                .andExpect(status().isMethodNotAllowed());
    }
}
