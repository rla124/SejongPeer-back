package com.sejong.sejongpeer.security.constant;

import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class WebSecurityURIs {
    public static final List<String> PUBLIC_URIS =
            List.of(
                    "/api/v1/auth/sign-in",
                    "/api/v1/member/sign-up",
                    "/api/v1/member/check-account",
                    "/api/v1/member/check-nickname",
                    "/api/v1/member/help/find-account",
                    "/api/v1/member/help/reset-password",
                    "/swagger-ui/**");
    public static final List<String> CORS_ALLOW_URIS =
            List.of("http://localhost:3000", "http://127.0.0.1:3000", "https://sejongpeer.com");
}
