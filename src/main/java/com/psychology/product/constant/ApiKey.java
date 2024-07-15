package com.psychology.product.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiKey {
    // Base URL for the API version 1
    private final String BASE_URL = "/api/v1/";

    // User-related endpoints
    public static final String USERS = BASE_URL + "users"; // Base URL for users
    public static final String USERS_PROFILE = "/profile"; // Endpoint for user profile
    public static final String USERS_PASSWORD_FORGOT = "/password/forgot"; // Endpoint for forgot password

    // Admin-related endpoints
    public static final String ADMIN = BASE_URL + "admin"; // Base URL for admin
    public static final String ADMIN_CLIENTS_ALL = "/clients/all"; // Endpoint to get all clients
    public static final String ADMIN_CLIENT_ID = "/client/{id}"; // Endpoint to get client by ID

    // Card-related endpoint
    public static final String CARD = BASE_URL + "card"; // Base URL for card

    // Diagnostic-related endpoints
    public static final String DIAGNOSTIC = BASE_URL + "diagnostic"; // Base URL for diagnostic
    public static final String DIAGNOSTIC_GET_ALL = "/get-all"; // Endpoint to get all diagnostics
    public static final String DIAGNOSTIC_ID = "/{id}"; // Endpoint to get diagnostic by ID
    public static final String DIAGNOSTIC_NEW = "/new"; // Endpoint to create new diagnostic
    public static final String DIAGNOSTIC_QUESTION_NEW = "/question/new"; // Endpoint to create new diagnostic question
    public static final String DIAGNOSTIC_ANSWER_NEW = "/answer/new"; // Endpoint to create new diagnostic answer
    public static final String DIAGNOSTIC_QUESTION_ID = "/question/{id}"; // Endpoint to get diagnostic question by ID
    public static final String DIAGNOSTIC_ANSWER_ID = "/answer/{id}"; // Endpoint to get diagnostic answer by ID
    public static final String DIAGNOSTIC_RESULT = "/result"; // Endpoint to get diagnostic result

    // Authentication-related endpoints
    public static final String AUTH = BASE_URL + "auth"; // Base URL for authentication
    public static final String AUTH_SIGNUP = "/signup"; // Endpoint for user signup
    public static final String AUTH_LOGIN = "/login"; // Endpoint for user login
    public static final String AUTH_REFRESH_ACCESS_TOKEN = "/refresh/access-token"; // Endpoint to refresh access token
    public static final String AUTH_REFRESH_REFRESH_TOKEN = "/refresh/refresh-token"; // Endpoint to refresh refresh token
    public static final String AUTH_ACTIVE_CODE = "/activate/{code}"; // Endpoint to activate account with code
    public static final String AUTH_LOGOUT = "/logout"; // Endpoint for user logout

}
