package com.example.tsgbackend.common.utils;

import com.example.tsgbackend.common.constant.SecurityConstants;
import com.example.tsgbackend.common.exception.BadRequestException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.CollectionUtils;

import javax.xml.bind.DatatypeConverter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JwtUtil {

    private static final byte[] secretKey = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);

    private static final byte[] refreshKey = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_REFRESH_KEY);

    private JwtUtil() {
        throw new IllegalStateException("instance creating forbidden");
    }

    /**
     * @Description: Generate Token according to user and role
     * @Param: [userName, roles, isRemember]
     * @Return: java.lang.String
     */
    public static String generateToken(String userName, List<String> roles, Boolean isRemember) throws BadRequestException {
        try {
            long expirationTime = isRemember ? SecurityConstants.TOKEN_EXPIRATION_REMEMBER_TIME : SecurityConstants.TOKEN_EXPIRATION_TIME;

            return Jwts.builder()
                    .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                    .signWith(Keys.hmacShaKeyFor(secretKey))
                    .setSubject(userName)
                    .claim(SecurityConstants.TOKEN_ROLE_CLAIM, roles)
                    .setIssuer(SecurityConstants.TOKEN_ISSUER)
                    .setIssuedAt(new Date())
                    .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                    .compact();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * @Description: Generate REFRESH_TOKEN
     * @Param: [userName]
     * @Return: java.lang.String
     */
    public static String getRefreshToken(String userName) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(refreshKey))
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION_REMEMBER_TIME * 1000))
                .compact();
    }

    /**
     * @Description: Verify Token
     * @Param: [token]
     * @Return: boolean
     */
    public static boolean verifyToken(String token) throws BadRequestException {
        try {
            getTokenBody(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("token expired: {} failed: {}", token, e.getMessage());
            throw new BadRequestException(e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("unsupported token: {} failed: {}", token, e.getMessage());
            throw new BadRequestException(e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("token malformed: {} failed: {}", token, e.getMessage());
            throw new BadRequestException(e.getMessage());
        } catch (SignatureException e) {
            log.warn("invalid token: {} failed: {}", token, e.getMessage());
            throw new BadRequestException(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("empty token: {} failed {}", token, e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * @Description: Get authentication info according to token
     * @Param: [token]
     * @Return: org.springframework.security.core.Authentication
     */
    public static Authentication getAuthentication(String token) {
        Claims claims;
        try {
            claims = getTokenBody(token);
        } catch(ExpiredJwtException e) {
            e.printStackTrace();
            claims = e.getClaims();
        }

        //Get user role
        List<String> roles = (List<String>) claims.get(SecurityConstants.TOKEN_ROLE_CLAIM);
        List<SimpleGrantedAuthority> authorities =
                CollectionUtils.isEmpty(roles) ? Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) :
                        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        //Get username
        User principal = new User(claims.getSubject(), "******", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**
     * @Description: Get user info from token
     * @Param: [token]
     * @Return: io.jsonwebtoken.Claims
     */
    private static Claims getTokenBody(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    /**
     * @Description: Get user info from REFRESH_TOKEN
     * @Param: [refreshToken]
     * @Return: io.jsonwebtoken.Claims
     */
    public static Claims getRefreshTokenBody(String refreshToken) {
        return Jwts.parserBuilder()
                .setSigningKey(refreshKey)
                .build()
                .parseClaimsJwt(refreshToken)
                .getBody();
    }
}
