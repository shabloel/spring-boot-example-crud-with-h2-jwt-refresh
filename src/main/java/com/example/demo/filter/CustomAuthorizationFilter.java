package com.example.demo.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getServletPath().equals("/api/login") ||
                httpServletRequest.getServletPath().equals("/api/token/refresh")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String userName = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
                    Arrays.stream(roles).forEach(role -> {
                        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userName, null, simpleGrantedAuthorities);
                    //this is how we tell Spring security, this is the user, these are his roles etc.
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                } catch (Exception e) {
                    log.error("Error logging in: {}", e.getMessage());
                    httpServletResponse.setHeader("error", e.getMessage());
                    httpServletResponse.setStatus(FORBIDDEN.value());
                    //httpServletResponse.sendError(FORBIDDEN.value());

                    Map<String, String> error = new HashMap<>();
                    error.put("error", e.getMessage());
                    httpServletResponse.setContentType(APPLICATION_JSON_VALUE);

                    new ObjectMapper().writeValue(httpServletResponse.getOutputStream(), error);

                }
            } else {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        }

    }
}
