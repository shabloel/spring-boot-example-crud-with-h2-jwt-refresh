package com.example.demo.filter;

import com.example.demo.services.HelperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final HelperService helperService;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, HelperService helperService) {
        this.authenticationManager = authenticationManager;
        this.helperService = helperService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        //use x-www-form-urlencoded in Postman
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Username is: {}", userName);
        log.info("Password is: {}", password);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String access_token = helperService.createToken(request, authResult);
        String refresh_token = helperService.createToken(request, authResult);

        //sends the JWT in the Header
/*        response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);*/

        helperService.sentTokens(access_token, refresh_token, response);

    }
}
