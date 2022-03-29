package io.github.nosiguapo.ap4medecins.services;

import io.github.nosiguapo.ap4medecins.entities.AppUser;
import io.github.nosiguapo.ap4medecins.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
// Logs
@Slf4j
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username);
        if (user == null){
            log.error("User not found.");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User {} found.", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // Returning a springsecurity user
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

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
