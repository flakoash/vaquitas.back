package com.example.vaquitasback.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.vaquitasback.entity.User;
import com.example.vaquitasback.repository.UserRepository;
import com.example.vaquitasback.service.ApplicationUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {

        try {
            User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);
            logger.debug(creds.toString());
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        String token = JWT.create()
                .withSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));

        logger.debug("token: " + token);
        String uname = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
        Long userId = userRepository.findByUsername(uname).getId();

//        JSONObject responseObject =  new JSONObject();
//        responseObject.put("id", userId);
//        responseObject.put("username", uname);
//        responseObject.put("token", token);

        String resJson = "{\n" +
                "  \"id\": " + userId + ",\n" +
                "  \"username\": \"" + uname + "\",\n" +
                "  \"token\": \"" + token + "\"\n" +
                "}";


        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(resJson);
        res.getWriter().flush();
    }
}
