package com.psychology.product.user;

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

import static com.psychology.product.data.UserData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void testSignUpUserInvalidPassword() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(getValidFirstName(), getValidLastName(), getValidEmail(), getInvalidPassword(), getValidPhoneNumber()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void testSignUpUserInvalidEmail() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(getValidFirstName(), getValidLastName(), getInvalidEmail(), getValidPassword(), getValidPhoneNumber()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    public void testSignUpUserEmptyFirstName() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(getEmptyData(), getValidLastName(), getValidEmail(), getValidPassword(), getValidPhoneNumber()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    public void testSignUpUserEmptyLastName() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(getValidFirstName(), getEmptyData(), getValidEmail(), getValidPassword(), getValidPhoneNumber()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    public void testSignUpUserInvalidPhoneNumber() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(getValidFirstName(), getValidLastName(), getValidEmail(), getValidPassword(), getInvalidPhoneNumber()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(7)
    public void testLoginUserSuccess() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getValidLoginRequest())))
                .andExpect(status().isOk());
    }

    @Test
    @Order(10)
    public void testLoginUserInvalidEmail() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest(getInvalidEmail(), getValidPassword()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(15)
    public void testLoginUserInvalidPassword() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest(getValidEmail(), getInvalidPassword()))))
                .andExpect(status().isUnauthorized());
    }
}
