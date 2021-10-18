package com.example.demo.services;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface HelperService {

    public String getToken(HttpServletRequest request, HttpServletResponse response);

    public String createToken(DecodedJWT decodedJWT, HttpServletRequest request, UserService userService);

    public String createToken(HttpServletRequest request, Authentication authentication);

    public void sentTokens(String accessToken, String refreshToken, HttpServletResponse response);

    public void createAndSentTokens(HttpServletRequest request, HttpServletResponse response, UserService service);

    public DecodedJWT getDecodedJWT(String token);

    public Algorithm getAlgorithm();
}

