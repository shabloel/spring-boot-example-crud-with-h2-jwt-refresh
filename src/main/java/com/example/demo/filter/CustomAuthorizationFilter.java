package com.example.demo.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.services.HelperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final HelperService helperService;

    public CustomAuthorizationFilter(HelperService helperService) {
        this.helperService = helperService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (httpServletRequest.getServletPath().equals("/api/newlogin") ||
                httpServletRequest.getServletPath().equals("/api/token/refresh")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            try {
                String token = helperService.getToken(httpServletRequest, httpServletResponse);
                DecodedJWT decodedJWT = helperService.getDecodedJWT(token);
                String userName = decodedJWT.getSubject();

                Collection<SimpleGrantedAuthority> simpleGrantedAuthorities =
                        getSimpleGranteAuthprities(decodedJWT);

                createAuthenticationtoken(userName, simpleGrantedAuthorities);

                filterChain.doFilter(httpServletRequest, httpServletResponse);


            } catch (Exception e) {
                createErrorResponse(httpServletResponse, e);
            }
        }

    }

    private Collection<SimpleGrantedAuthority> getSimpleGranteAuthprities(DecodedJWT decodedJWT) {
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        Arrays.stream(roles).forEach(role -> {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        });
        return simpleGrantedAuthorities;
    }

    private void createAuthenticationtoken(String userName,
                                           Collection<SimpleGrantedAuthority> simpleGrantedAuthorities) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, null, simpleGrantedAuthorities);
        //this is how we tell Spring security, this is the user, these are his roles etc.
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public void createErrorResponse(HttpServletResponse response, Exception e) {
        log.error("Error logging in: {}", e.getMessage());
        response.setHeader("error", e.getMessage());
        response.setStatus(FORBIDDEN.value());
        //httpServletResponse.sendError(FORBIDDEN.value());

        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);

        try {
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

