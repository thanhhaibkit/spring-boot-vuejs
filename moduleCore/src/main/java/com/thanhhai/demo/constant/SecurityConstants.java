package com.thanhhai.demo.constant;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 3600_000; // 60 minutes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/v1/auth/register";
    public static final String LOG_IN_URL = "/api/v1/auth/login";
    public static final String SYNC_DATA_URL = "/api/v1/sync-data";
}