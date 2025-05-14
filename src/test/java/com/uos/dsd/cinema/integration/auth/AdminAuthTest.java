package com.uos.dsd.cinema.integration.auth;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.uos.dsd.cinema.integration.IntegrationTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.web.servlet.ResultActions;

public class AdminAuthTest extends IntegrationTest {

    private static final String ADMIN_NAME = "administrator";
    private static final String ADMIN_PASSWORD = "password123!";

    @Test
    public void signup_and_login_admin() throws Exception {

        // given
        String name = ADMIN_NAME;
        String password = ADMIN_PASSWORD;

        // when
        sendSignupAdmin(name, password)
                .andExpect(status().isOk());

        String response = sendLoginAdmin(name, password)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // then
        assertTrue(response.contains("\"code\":\"COM000\""));
        assertTrue(response.contains("\"message\":\"Success\""));
        assertTrue(response.contains("\"name\":\"" + name + "\""));
    }

    @Test
    public void signup_with_existing_name() throws Exception {

        // given
        String name = ADMIN_NAME;
        String password = ADMIN_PASSWORD;

        // when
        sendSignupAdmin(name, password)
                .andExpect(status().isOk());

        // then
        String response = sendSignupAdmin(name, password)
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        // then
        assertTrue(response.contains("\"code\":\"COM001\""));
        assertTrue(response.contains("\"message\":\"Admin name already exists\""));
    }

    @Test
    public void signup_with_invalid_name() throws Exception {
        // given
        String name = "short";
        String password = ADMIN_PASSWORD;

        // when
        String response = sendSignupAdmin(name, password)
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        // then
        assertTrue(response.contains("\"code\":\"COM001\""));
        assertTrue(response.contains("\"message\":\"Invalid name format\""));
    }

    @ParameterizedTest
    @ValueSource(strings = { "12345678", "abcdefgh", "!@#$%^&*()_+", "nospecials123", "!@!@1234", "nodigits!!!",
            "short!1", "한글", "space space" })
    public void signup_with_invalid_password(String password) throws Exception {
        // given
        String name = ADMIN_NAME;

        // when
        String response = sendSignupAdmin(name, password)
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        // then
        assertTrue(response.contains("\"code\":\"COM001\""));
        assertTrue(response.contains("\"message\":\"Invalid password format\""));
    }

    @Test
    public void login_with_invalid_name() throws Exception {
        // given
        String name = "invalid";
        String password = ADMIN_PASSWORD;

        // when
        String response = sendLoginAdmin(name, password)
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        // then
        assertTrue(response.contains("\"code\":\"COM003\""));
        assertTrue(response.contains("\"message\":\"Invalid admin name or password\""));
    }

    @Test
    public void login_with_invalid_password() throws Exception {
        // given
        sendSignupAdmin(ADMIN_NAME, ADMIN_PASSWORD)
                .andExpect(status().isOk());

        String name = ADMIN_NAME;
        String password = "invalid";

        // when
        String response = sendLoginAdmin(name, password)
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        // then
        assertTrue(response.contains("\"code\":\"COM003\""));
        assertTrue(response.contains("\"message\":\"Invalid admin name or password\""));
    }

    private ResultActions sendSignupAdmin(String name, String password) throws Exception {

        return mockMvc.perform(post("/admin/signup")
                .contentType("application/json")
                .content("{\"name\":\"" + name + "\",\"password\":\"" + password + "\"}"));
    }

    private ResultActions sendLoginAdmin(String name, String password) throws Exception {
        return mockMvc.perform(post("/admin/login")
                .contentType("application/json")
                .content("{\"name\":\"" + name + "\",\"password\":\"" + password + "\"}"));
    }
}
