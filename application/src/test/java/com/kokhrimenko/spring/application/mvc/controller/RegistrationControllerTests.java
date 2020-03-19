package com.kokhrimenko.spring.application.mvc.controller;

import static com.kokhrimenko.spring.application.mvc.MvcConfig.REGISTER_PAGE;
import static com.kokhrimenko.spring.application.mvc.controller.RegistrationController.PASSWORD_ERROR_MSG;
import static com.kokhrimenko.spring.application.mvc.controller.RegistrationController.PASSWORD_FIELD;
import static com.kokhrimenko.spring.application.mvc.controller.RegistrationController.REG_PAGE;
import static com.kokhrimenko.spring.application.mvc.controller.RegistrationController.USERNAME_ERROR_MSG;
import static com.kokhrimenko.spring.application.mvc.controller.RegistrationController.USERNAME_FIELD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.kokhrimenko.spring.application.Application;
import com.kokhrimenko.spring.application.domain.User;
import com.kokhrimenko.spring.application.mvc.controller.dto.UserRegistrationDTO;
import com.kokhrimenko.spring.application.service.UserService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = { Application.class })
@DisplayName("RegistrationController tests!")
public class RegistrationControllerTests {
    private static final String MODEL_ATTRIBUTE_NAME = "user";
    private static final String CONFIRM_PASSWORD_FIELD_NAME = "confirmPassword";
    private static final String WRONG_FIELD_VALUE_LENGTH_ERROR_CODE = "Size";
    private static final String NULL_VALUE_FIELD_ERROR_CODE = "NotNull";
    private static final String REGISTRATION_SUCCESS_URL = "/register?success";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> captor;

    @Test
    @DisplayName("Test RegistrationController.registerUserAccount - not equals password's.")
    void testRegisterUserAccountWithDifferentPasswords() throws Exception {
        final String password = "password",
                confirmPassword = "not the same",
                email = "email";
        UserRegistrationDTO userData = createDummyUser(password, confirmPassword, email);

        this.mvc.perform(
                post(REGISTER_PAGE).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param(PASSWORD_FIELD, userData.getPassword())
                    .param(CONFIRM_PASSWORD_FIELD_NAME, userData.getConfirmPassword())
                    .param(USERNAME_FIELD, userData.getEmail())
                    .sessionAttr(MODEL_ATTRIBUTE_NAME, new UserRegistrationDTO()))
                .andExpect(status().isOk()).andExpect(view().name(REG_PAGE))
                .andExpect(model().attributeHasFieldErrors(MODEL_ATTRIBUTE_NAME, PASSWORD_FIELD))
                .andExpect(model().attributeHasFieldErrorCode(MODEL_ATTRIBUTE_NAME, PASSWORD_FIELD, PASSWORD_ERROR_MSG))
                .andExpect(model().errorCount(1));
    }

    private UserRegistrationDTO createDummyUser(final String password, final String confirmPassword, final String email) {
        UserRegistrationDTO userData = new UserRegistrationDTO();
        userData.setPassword(password);
        userData.setConfirmPassword(confirmPassword);
        userData.setEmail(email);
        return userData;
    }

    @Test
    @DisplayName("Test RegistrationController.registerUserAccount - too short password.")
    void testRegisterUserAccountWithShortPasswords() throws Exception {
        final String password = "passwo",
                confirmPassword = "password",
                email = "email";
        UserRegistrationDTO userData = createDummyUser(password, confirmPassword, email);

        this.mvc.perform(
                post(REGISTER_PAGE).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param(PASSWORD_FIELD, userData.getPassword())
                    .param(CONFIRM_PASSWORD_FIELD_NAME, userData.getConfirmPassword())
                    .param(USERNAME_FIELD, userData.getEmail())
                    .sessionAttr(MODEL_ATTRIBUTE_NAME, new UserRegistrationDTO()))
                .andExpect(status().isOk()).andExpect(view().name(REG_PAGE))
                .andExpect(model().attributeHasFieldErrors(MODEL_ATTRIBUTE_NAME, PASSWORD_FIELD))
                .andExpect(model().attributeHasFieldErrorCode(MODEL_ATTRIBUTE_NAME, PASSWORD_FIELD, WRONG_FIELD_VALUE_LENGTH_ERROR_CODE))
                .andExpect(model().errorCount(1));
    }

    @Test
    @DisplayName("Test RegistrationController.registerUserAccount - null password.")
    void testRegisterUserAccountWithNullPasswords() throws Exception {
        final String password = null,
                confirmPassword = "not the same",
                email = "email";
        UserRegistrationDTO userData = createDummyUser(password, confirmPassword, email);

        this.mvc.perform(
                post(REGISTER_PAGE).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param(PASSWORD_FIELD, userData.getPassword())
                    .param(CONFIRM_PASSWORD_FIELD_NAME, userData.getConfirmPassword())
                    .param(USERNAME_FIELD, userData.getEmail())
                    .sessionAttr(MODEL_ATTRIBUTE_NAME, new UserRegistrationDTO()))
                .andExpect(status().isOk()).andExpect(view().name(REG_PAGE))
                .andExpect(model().attributeHasFieldErrors(MODEL_ATTRIBUTE_NAME, PASSWORD_FIELD))
                .andExpect(model().attributeHasFieldErrorCode(MODEL_ATTRIBUTE_NAME, PASSWORD_FIELD, NULL_VALUE_FIELD_ERROR_CODE))
                .andExpect(model().errorCount(1));
    }

    @Test
    @DisplayName("Test RegistrationController.registerUserAccount - too short email.")
    void testRegisterUserAccountWithShortUsername() throws Exception {
        final String password = "password",
                confirmPassword = password,
                email = "";
        UserRegistrationDTO userData = createDummyUser(password, confirmPassword, email);

        this.mvc.perform(
                post(REGISTER_PAGE).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param(PASSWORD_FIELD, userData.getPassword())
                    .param(CONFIRM_PASSWORD_FIELD_NAME, userData.getConfirmPassword())
                    .param(USERNAME_FIELD, userData.getEmail())
                    .sessionAttr(MODEL_ATTRIBUTE_NAME, new UserRegistrationDTO()))
                .andExpect(status().isOk()).andExpect(view().name(REG_PAGE))
                .andExpect(model().attributeHasFieldErrors(MODEL_ATTRIBUTE_NAME, USERNAME_FIELD))
                .andExpect(model().attributeHasFieldErrorCode(MODEL_ATTRIBUTE_NAME, USERNAME_FIELD, WRONG_FIELD_VALUE_LENGTH_ERROR_CODE))
                .andExpect(model().errorCount(1));
    }

    @Test
    @DisplayName("Test RegistrationController.registerUserAccount - null email.")
    void testRegisterUserAccountWithNullUsername() throws Exception {
        final String password = "password",
                confirmPassword = password,
                email = null;
        UserRegistrationDTO userData = createDummyUser(password, confirmPassword, email);

        this.mvc.perform(
                post(REGISTER_PAGE).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param(PASSWORD_FIELD, userData.getPassword())
                    .param(CONFIRM_PASSWORD_FIELD_NAME, userData.getConfirmPassword())
                    .param(USERNAME_FIELD, userData.getEmail())
                    .sessionAttr(MODEL_ATTRIBUTE_NAME, new UserRegistrationDTO()))
                .andExpect(status().isOk()).andExpect(view().name(REG_PAGE))
                .andExpect(model().attributeHasFieldErrors(MODEL_ATTRIBUTE_NAME, USERNAME_FIELD))
                .andExpect(model().attributeHasFieldErrorCode(MODEL_ATTRIBUTE_NAME, USERNAME_FIELD, NULL_VALUE_FIELD_ERROR_CODE))
                .andExpect(model().errorCount(1));
    }

    @Test
    @DisplayName("Test RegistrationController.registerUserAccount - email already in use.")
    void testRegisterUserAccountWithAlreadyExistsUsername() throws Exception {
        final String password = "password",
                confirmPassword = password,
                email = "email";
        UserRegistrationDTO userData = createDummyUser(password, confirmPassword, email);

        when(userService.loadUserByEmail(userData.getEmail())).thenReturn(new User());

        this.mvc.perform(
                post(REGISTER_PAGE).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param(PASSWORD_FIELD, userData.getPassword())
                    .param(CONFIRM_PASSWORD_FIELD_NAME, userData.getConfirmPassword())
                    .param(USERNAME_FIELD, userData.getEmail())
                    .sessionAttr(MODEL_ATTRIBUTE_NAME, new UserRegistrationDTO()))
                .andExpect(status().isOk()).andExpect(view().name(REG_PAGE))
                .andExpect(model().attributeHasFieldErrors(MODEL_ATTRIBUTE_NAME, USERNAME_FIELD))
                .andExpect(model().attributeHasFieldErrorCode(MODEL_ATTRIBUTE_NAME, USERNAME_FIELD, USERNAME_ERROR_MSG))
                .andExpect(model().errorCount(1));
    }

    @Test
    @DisplayName("Test RegistrationController.registerUserAccount - success case.")
    void testRegisterUserAccountSuccess() throws Exception {
        final String password = "password",
                confirmPassword = password,
                email = "email";
        UserRegistrationDTO userData = createDummyUser(password, confirmPassword, email);

        this.mvc.perform(
                post(REGISTER_PAGE).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param(PASSWORD_FIELD, userData.getPassword())
                    .param(CONFIRM_PASSWORD_FIELD_NAME, userData.getConfirmPassword())
                    .param(USERNAME_FIELD, userData.getEmail())
                    .sessionAttr(MODEL_ATTRIBUTE_NAME, new UserRegistrationDTO()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(REGISTRATION_SUCCESS_URL));

        verify(userService, times(1)).createUser(captor.capture());
        assertEquals(userData.getEmail(), captor.getValue().getEmail());
    }
}
