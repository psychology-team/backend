package com.psychology.product.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiKey {

    private final String BASE_URL = "/api/v1/";

    public static final String USERS = BASE_URL + "users";
    public static final String USERS_PROFILE = "/profile";
    public static final String USERS_PASSWORD_FORGOT = "/password/forgot";

    public static final String ADMIN = BASE_URL + "admin";
    public static final String ADMIN_CLIENTS_ALL = "/clients/all";
    public static final String ADMIN_CLIENT_ID = "/client/{id}";

    public static final String CARD = BASE_URL + "card";

    public static final String DIAGNOSTIC = BASE_URL + "diagnostic";
    public static final String DIAGNOSTIC_GET_ALL = "/get/all";
    public static final String DIAGNOSTIC_ID = "/{id}";
    public static final String DIAGNOSTIC_NEW = "/new";
    public static final String DIAGNOSTIC_QUESTION_NEW = "/question/new";
    public static final String DIAGNOSTIC_ANSWER_NEW = "/answer/new";
    public static final String DIAGNOSTIC_QUESTION_ID = "/question/{id}";
    public static final String DIAGNOSTIC_ANSWER_ID = "/answer/{id}";
    public static final String DIAGNOSTIC_RESULT = "/result/";

    public static final String AUTH = BASE_URL + "auth";
    public static final String AUTH_SIGNUP = "/signup";
    public static final String AUTH_LOGIN = "/login";
    //todo can i rename pass rotes???
    public static final String AUTH_REFRESH_ACCESS_TOKEN = "/refresh/access-token";
    public static final String AUTH_REFRESH_REFRESH_TOKEN = "/refresh/refresh-token";
    public static final String AUTH_ACTIVE_CODE = "/activate/{code}";
    public static final String AUTH_LOGOUT = "/logout";



}
