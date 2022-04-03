package io.github.nosiguapo.ap4medecins.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.nosiguapo.ap4medecins.entities.AppUser;
import io.github.nosiguapo.ap4medecins.repositories.AppUserRepository;
import io.github.nosiguapo.ap4medecins.services.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// logs
@Slf4j
public class AuthFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final AppUserService appUserService;

    public AuthFilter(AuthenticationManager authenticationManager, AppUserService appUserService){
        this.authenticationManager = authenticationManager;
        this.appUserService = appUserService;
    }

    // When user tries to log in
    // Grabing the infos from the request, and passing them to the token in order to it to be generated
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Username: {}", username);
        log.info("Password: {}", password);
        // Token generation
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // Check if token is correct
        return authenticationManager.authenticate(authenticationToken);
    }

    // When login successful
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // / Token test
        // |
        // | https://jwt.io/

        // Giving access (and refresh) token once login successful
        // The user specified here is the Spring boot security user, not the AppUser we created
        User user = (User)authentication.getPrincipal();
        AppUser detailsUser = appUserService.getUser(user.getUsername());
        Algorithm algorithm = Algorithm.HMAC256("5CD423eiSJdxx6qd".getBytes());
        // Access
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                // Adding more infos to token
                .withClaim("username", user.getUsername())
                .withClaim("fname", detailsUser.getFname())
                .withClaim("lname", detailsUser.getLname())
                // In milliseconds: 60 sec * 65 (Ap. an hour long token)
                .withExpiresAt(new Date(System.currentTimeMillis()+ 1000 * 60 * 65))
//                .withExpiresAt(new Date(System.currentTimeMillis()+ 1000 * 5))
                .withIssuer(request.getRequestURI())
                .withClaim(
                        "roles",
                        user.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority
                        ).collect(Collectors.toList())
                )
                .sign(algorithm);

        // Refresh
        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withClaim("username", user.getUsername())
                .withClaim("fname", detailsUser.getFname())
                .withClaim("lname", detailsUser.getLname())
                // A month long (Refresh token is always more persistant)
                .withExpiresAt(new Date(System.currentTimeMillis()+ 1000L * 3600 * 24 * 31))
                .withIssuer(request.getRequestURI())
                .withClaim(
                        "roles",
                        user.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority
                        ).collect(Collectors.toList())
                )
                .sign(algorithm);

        // TOKEN INSIDE HEADER
//        response.setHeader("access_token", access_token);
//        response.setHeader("refresh_token", refresh_token);

        // TOKEN INSIDE BODY
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
