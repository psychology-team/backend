package com.psychology.product.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psychology.product.controller.request.LoginRequest;
import com.psychology.product.controller.request.SignUpRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.psychology.product.data.UserData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class AuthAndUserControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String jwtAccessToken;

    @Test
    @Order(1)
    public void testSignUpUserSuccess() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getValidSignUpRequest())))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void testSignUpUserDuplicateConflict() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getValidSignUpRequest())))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(3)
    public void testSignUpUserInvalidPassword() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(getValidFirstName(), getValidLastName(), getValidEmail(), getInvalidPassword(), getValidPhoneNumber()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    public void testSignUpUserInvalidEmail() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(getValidFirstName(), getValidLastName(), getInvalidEmail(), getValidPassword(), getValidPhoneNumber()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    public void testSignUpUserEmptyFirstName() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(getEmptyData(), getValidLastName(), getValidEmail(), getValidPassword(), getValidPhoneNumber()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    public void testSignUpUserEmptyLastName() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(getValidFirstName(), getEmptyData(), getValidEmail(), getValidPassword(), getValidPhoneNumber()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(7)
    public void testSignUpUserInvalidPhoneNumber() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(getValidFirstName(), getValidLastName(), getValidEmail(), getValidPassword(), getInvalidPhoneNumber()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(8)
    public void testLoginUserSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getValidLoginRequest())))
                .andExpect(status().isOk()).andReturn();
        JsonNode jsonNode = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        jwtAccessToken = jsonNode.get("data").get("jwtAccessToken").asText();
    }

    @Test
    @Order(20)
    public void testLoginUserInvalidEmail() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest(getInvalidEmail(), getValidPassword()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(30)
    public void testLoginUserInvalidPassword() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest(getValidEmail(), getInvalidPassword()))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(40)
    public void testAccessToSecurePointWithValidToken() throws Exception {
        mockMvc.perform(get("/auth/security-point")
                        .header("Authorization", String.format("Bearer %s", jwtAccessToken)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(41)
    public void testAccessToSecurePointWithoutToken() throws Exception {
        mockMvc.perform(get("/auth/security-point"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(50)
    public void testUserDeleteSuccessWithValidToken() throws Exception {
        mockMvc.perform(delete("/user/")
                        .header("Authorization", String.format("Bearer %s", jwtAccessToken)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(51)
    public void testUserAlreadyDeletedWithValidToken() throws Exception {
        mockMvc.perform(delete("/user/")
                        .header("Authorization", String.format("Bearer %s", jwtAccessToken)))
                .andExpect(status().isUnauthorized());
    }
}
