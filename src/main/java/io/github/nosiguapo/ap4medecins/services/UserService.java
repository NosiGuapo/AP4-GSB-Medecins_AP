package io.github.nosiguapo.ap4medecins.services;

import io.github.nosiguapo.ap4medecins.entities.User;
import io.github.nosiguapo.ap4medecins.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        // "%" are mentionned inside the services in order to be more flexible with the like method we want to use
        return userRepository.findByLnameLike("%"+name+"%");
    }

    public List<User> getUsersByFname(String fname){
        return userRepository.findByFnameLike("%"+fname+"%");
    }

    public List<User> getUserByUsername(String username){
        return userRepository.findByUsernameLike("%"+username+"%");
    }

//    public List<User> getUserByNameAndFname(String request){
//        return userRepository.findByLnameAndFname(request);
//    }

    public List<User> getUserCreatedBetween(Date date_1, Date date_2){
        return userRepository.findByRegisterIsBetween(date_1, date_2);
    }
}
