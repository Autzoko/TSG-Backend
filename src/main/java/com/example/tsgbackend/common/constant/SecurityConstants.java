package com.example.tsgbackend.common.constant;

public class SecurityConstants {
    private SecurityConstants() {
        throw new IllegalStateException("cannot create static constant");
    }

    /**
     * Login URL
     */
    public static final String USER_LOGIN_URL = "/user/login";

    /**
     * JWT Sign Key
     * USING: HS512
     */
    public static final String JWT_SECRET_KEY = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiSm9obiIsImFkbWluIjp0cnVlfQ.Vv1PSXfbHj-8I4iBth47WoHwuV9dMpACFv_cmgnTGf-j0iOgux0LrVrgpZOOQ6vjcQqifddDbX2Yx1Qoec5Q1g";

    /**
     * Refresh Token Key
     */
    public static final String JWT_REFRESH_KEY = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiSm9obiIsImFkbWluIjp0cnVlfQ.cNP9_SgQMkzvwX4yYkXSRIS_YhyhqnPjDKYY8mXCqCnvuoNnn_Z4YqYI4EiEWwbMBoS5LC3J-kCZgIBrSCA59Q";

    /**
     * Token Prefix
     * Used in header for authorization
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Request Header
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * Token Type
     */
    public static final String TOKEN_TYPE = "JWT";

    /**
     * Claim UserDto Role
     */
    public static final String TOKEN_ROLE_CLAIM = "role";

    /**
     * Token Issuer Identification
     */
    public static final String TOKEN_ISSUER = "security";

    /**
     * Token Audience
     */
    public static final String TOKEN_AUDIENCE = "security-all";

    /**
     * Token Expiration Time
     * 2 hours
     */
    public static final Long TOKEN_EXPIRATION_TIME = 60 * 60 * 2L;

    /**
     * Token Expiration Remember Time
     * 7 days
     */
    public static final Long TOKEN_EXPIRATION_REMEMBER_TIME = 60 * 60 * 24 * 7L;

}
