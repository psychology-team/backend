package com.psychology.product.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psychology.product.constant.ApiKey;
import com.psychology.product.controller.request.LoginRequest;
import com.psychology.product.controller.request.SignUpRequest;
import com.psychology.product.repository.UserRepository;
import com.psychology.product.repository.model.User;
import lombok.SneakyThrows;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;


    @Test
    @Order(1)
    @SneakyThrows
    public void signup_User_Success() {
        mockMvc.perform(post(ApiKey.AUTH + ApiKey.AUTH_SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getValidSignUpRequest())))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    @SneakyThrows
    public void signup_User_Duplicate_Conflict() {
        mockMvc.perform(post(ApiKey.AUTH + ApiKey.AUTH_SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getValidSignUpRequest())))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(3)
    @SneakyThrows
    public void signup_User_Invalid_Password() {
        mockMvc.perform(post(ApiKey.AUTH + ApiKey.AUTH_SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(getValidFirstName(), getValidLastName(), getValidEmail(), getInvalidPassword(), getValidPhoneNumber()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    @SneakyThrows
    public void signup_User_Invalid_Email() {
        mockMvc.perform(post(ApiKey.AUTH + ApiKey.AUTH_SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(getValidFirstName(), getValidLastName(), getInvalidEmail(), getValidPassword(), getValidPhoneNumber()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    @SneakyThrows
    public void signup_User_Empty_FirstName() {
        mockMvc.perform(post(ApiKey.AUTH + ApiKey.AUTH_SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(getEmptyData(), getValidLastName(), getValidEmail(), getValidPassword(), getValidPhoneNumber()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    @SneakyThrows
    public void signup_User_Empty_LastName() {
        mockMvc.perform(post(ApiKey.AUTH + ApiKey.AUTH_SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(getValidFirstName(), getEmptyData(), getValidEmail(), getValidPassword(), getValidPhoneNumber()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(7)
    @SneakyThrows
    public void signup_User_Invalid_PhoneNumber() {
        mockMvc.perform(post(ApiKey.AUTH + ApiKey.AUTH_SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(getValidFirstName(), getValidLastName(), getValidEmail(), getValidPassword(), getInvalidPhoneNumber()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    @Order(20)
    public void login_User_Success() {
        MvcResult mvcResult = mockMvc.perform(post(ApiKey.AUTH + ApiKey.AUTH_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getValidLoginRequest())))
                .andExpect(status().isOk()).andReturn();
        JsonNode jsonNode = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        jwtAccessToken = jsonNode.get("data").get("jwtAccessToken").asText();
    }


    @Test
    @Order(9)
    @SneakyThrows
    public void login_User_Invalid_Email() {
        mockMvc.perform(post(ApiKey.AUTH + ApiKey.AUTH_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest(getInvalidEmail(), getValidPassword()))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(10)
    @SneakyThrows
    public void login_User_Invalid_Password() {
        mockMvc.perform(post(ApiKey.AUTH + ApiKey.AUTH_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest(getValidEmail(), getInvalidPassword()))))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @Order(11)
    @SneakyThrows
    public void activateUser() {
        User user = userRepository.findByEmail(getValidSignUpRequest().email()).orElseThrow(() -> new RuntimeException("User not found"));
        String uniqueCode = user.getUniqueCode();

        mockMvc.perform(post(ApiKey.AUTH + ApiKey.AUTH_ACTIVE_CODE, uniqueCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(12)
    @SneakyThrows
    public void activateUser_NotFound() {
        User user = userRepository.findByEmail(getValidSignUpRequest().email()).orElseThrow(() -> new RuntimeException("User not found"));
        String uniqueCode = user.getUniqueCode();

        mockMvc.perform(post(ApiKey.AUTH + ApiKey.AUTH_ACTIVE_CODE, uniqueCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}