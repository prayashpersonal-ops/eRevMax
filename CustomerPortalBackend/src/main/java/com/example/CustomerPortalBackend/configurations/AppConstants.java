package com.example.CustomerPortalBackend.configurations;

import java.util.UUID;

public class AppConstants {
    public static final String[] AUTH_PUBLIC_URL = {
            "/admin/login",
            "/user/register","/user/login", "/user/refresh"
    };

    public static UUID parseUUID(String uuid) {
        return UUID.fromString(uuid);
    }
}
