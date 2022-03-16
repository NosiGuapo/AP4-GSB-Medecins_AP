package io.github.nosiguapo.ap4medecins.controllers;

import io.github.nosiguapo.ap4medecins.entities.User;
import io.github.nosiguapo.ap4medecins.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/gsb/utilisateurs")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getAll(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    // Getting the id precised in the url and turning it into a variable
    public User get(@PathVariable("id") Long id){
        return userService.getUserById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/lname/{lname}")
    public List<User> getByLname(@PathVariable("lname") String name){
        // No responsestatus 404 if a list is sent
        return userService.getUsersByName(name);
    }

    @GetMapping("/fname/{fname}")
    public List<User> getByFname(@PathVariable("fname") String name){
        // No responsestatus 404 if a list is sent
        return userService.getUsersByFname(name);
    }

    @GetMapping("/username/{username}")
    public List<User> getByUsername(@PathVariable("username") String uname){
        // No responsestatus 404 if a list is sent
        return userService.getUserByUsername(uname);
    }
}
