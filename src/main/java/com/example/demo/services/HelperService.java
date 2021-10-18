package com.example.demo.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface HelperService {

    public String getRefreshToken(HttpServletRequest request, HttpServletResponse response);

    public DecodedJWT getAccessToken(HttpServletRequest request, HttpServletResponse response);

    public String createToken(DecodedJWT decodedJWT, HttpServletRequest request, UserService userService);

    public String createToken(HttpServletRequest request, Authentication authentication);

    public void sentTokens(String accessToken, String refreshToken, HttpServletResponse response);

    public void createAndSentTokens(HttpServletRequest request, HttpServletResponse response, UserService service);
}

