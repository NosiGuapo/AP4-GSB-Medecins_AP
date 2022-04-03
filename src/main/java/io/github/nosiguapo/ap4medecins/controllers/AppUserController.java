package io.github.nosiguapo.ap4medecins.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.nosiguapo.ap4medecins.entities.AppUser;
import io.github.nosiguapo.ap4medecins.entities.Role;
import io.github.nosiguapo.ap4medecins.services.AppUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/gsb")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("/utilisateurs")
    public ResponseEntity<List<AppUser>>getUsers(){
        return ResponseEntity.ok().body(appUserService.getUsers());
    }

    @PostMapping(path = "/utilisateurs", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AppUser>saveUser(@RequestBody AppUser user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/gsb/utilisateurs").toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveUser(user));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form){
        appUserService.addRoleToUser(form.getUsername(), form.getRolename());
        return ResponseEntity.ok().build();
    }

    // / RefreshToken usage
    // | When a request is sent, the client will use the access token in priority
    // | However, this action token lasts for a pretty short period of time
    // | We want to send requests using this refresh token if the other token is expired
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorization = request.getHeader(AUTHORIZATION);
        if (authorization != null && authorization.startsWith("GSB_WT ")){
            try {
                String refresh_token = authorization.substring("GSB_WT ".length());
                Algorithm algorithm = Algorithm.HMAC256("5CD423eiSJdxx6qd".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                // Checking if the user exists
                AppUser aUser = appUserService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(aUser.getUsername())
                        .withClaim("username", aUser.getUsername())
                        .withClaim("fname", aUser.getFname())
                        .withClaim("lname", aUser.getLname())
                        // In milliseconds: 60 sec * 65 (Ap. an hour long refresh_token)
                        .withExpiresAt(new Date(System.currentTimeMillis()+ 1000 * 60 * 65))
//                        .withExpiresAt(new Date(System.currentTimeMillis()+ 1000 * 5))
                        .withIssuer(request.getRequestURI())
                        .withClaim(
                                "roles",
                                aUser.getRoles().stream().map(
                                        Role::getName
                                ).collect(Collectors.toList())
                        )
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("An error occured while attempting to logging you on", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);

            }
        } else {
            throw new RuntimeException("Refresh token is invalid or missing");
        }
    }
}

@Data
class RoleToUserForm{
    private String username;
    private String rolename;
}
