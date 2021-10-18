package com.example.demo.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class HelperServiceImpl implements HelperService {

    private final UserService userService;

    public HelperServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void createAndSentTokens(HttpServletRequest request,
                                    HttpServletResponse response,
                                    UserService userService) {
        String refreshToken = getToken(request, response);
        DecodedJWT decodedJWT = getDecodedJWT(refreshToken);
        String accessToken = createToken(decodedJWT, request, userService);
        sentTokens(accessToken, refreshToken, response);
    }

    @Override
    public String getToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring("Bearer ".length());
        } else {
            throw new RuntimeException("Token is missing");
        }
    }

    @Override
    public String createToken(DecodedJWT decodedJWT, HttpServletRequest request, UserService userService) {
        String userName = decodedJWT.getSubject();
        User user = userService.getUser(userName).get();
        return JWT.create()
                .withSubject(user.getUserName())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getRoles().stream().map(Role::getRoleName)
                        .collect(Collectors.toList()))
                .sign(getAlgorithm());
    }

    @Override
    public String createToken(HttpServletRequest request, Authentication authentication) {
        org.springframework.security.core.userdetails.User userDetails =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        Algorithm algorithm = getAlgorithm();
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);
    }

    @Override
    public void sentTokens(String accessToken, String refreshToken, HttpServletResponse response) {
        try {
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", accessToken);
            tokens.put("refresh_token", refreshToken);
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public DecodedJWT getDecodedJWT(String token) {
        Algorithm algorithm = getAlgorithm();
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}
