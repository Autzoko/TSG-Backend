package com.example.tsgbackend.config.filter;

import com.example.tsgbackend.common.constant.SecurityConstants;
import com.example.tsgbackend.common.utils.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            System.out.println("Request URL: " + request.getRequestURI());

            //GET TOKEN FROM REQUEST
            String token = this.getTokenFromHttpServletRequest(request);
            if(StringUtil.isBlank(token) || token.length() < 150) {
                filterChain.doFilter(request, response);
                return;
            }
            // Is Token Valid

        }
    }

    private String getTokenFromHttpServletRequest(HttpServletRequest request) {
        // Get token from Authorization
        String authorization = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if(StringUtils.isNotBlank(authorization) && authorization.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return authorization.replace(SecurityConstants.TOKEN_PREFIX, "");
        }
        return null;
    }
}
