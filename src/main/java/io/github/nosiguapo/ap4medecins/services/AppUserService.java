package io.github.nosiguapo.ap4medecins.services;

import io.github.nosiguapo.ap4medecins.entities.AppUser;
import io.github.nosiguapo.ap4medecins.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
// Logs
@Slf4j
public class AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUser saveUser(AppUser user){
        log.info("Saving new user {} to database", user.getUsername());
        return appUserRepository.save(user);
    }

    public AppUser getUser(String username){
        return appUserRepository.findByUsername(username);
    }

    public List<AppUser> getUsers(){
        return appUserRepository.findAll();
    }
}
