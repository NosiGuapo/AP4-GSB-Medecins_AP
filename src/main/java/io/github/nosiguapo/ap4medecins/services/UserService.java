package io.github.nosiguapo.ap4medecins.services;

import io.github.nosiguapo.ap4medecins.entities.User;
import io.github.nosiguapo.ap4medecins.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public List<User> getUsersByName(String name){
        return userRepository.findByLname(name);
    }

    public List<User> getUsersByFname(String fname){
        return userRepository.findByFname(fname);
    }

    public List<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> getUserByNameAndFname(String request){
        return userRepository.findByLnameAndFname(request);
    }
}
