package io.github.nosiguapo.ap4medecins.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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
import java.util.stream.Collectors;

// logs
@Slf4j
public class AuthFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public AuthFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
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
        // Giving access (and refresh) token once login successful
        // The user specified here is the Spring boot security user, not the AppUser we created
        User user = (User)authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("5CD423eiSJdxx6qd".getBytes());
        // Access
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                // In milliseconds: 60 sec * 65 (Ap. an hour long token)
                .withExpiresAt(new Date(System.currentTimeMillis()+ 1000 * 60 * 65))
                .withIssuer(request.getRequestURI().toString())
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
                // A month long (Refresh token is always more persistant)
                .withExpiresAt(new Date(System.currentTimeMillis()+ 1000L * 3600 * 24 * 31))
                .withIssuer(request.getRequestURI().toString())
                .withClaim(
                        "roles",
                        user.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority
                        ).collect(Collectors.toList())
                )
                .sign(algorithm);

        response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);
    }
}
