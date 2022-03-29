package io.github.nosiguapo.ap4medecins.controllers;

import io.github.nosiguapo.ap4medecins.entities.AppUser;
import io.github.nosiguapo.ap4medecins.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
}
