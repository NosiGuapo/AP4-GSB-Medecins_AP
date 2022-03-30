package io.github.nosiguapo.ap4medecins.services;

import io.github.nosiguapo.ap4medecins.entities.AppUser;
import io.github.nosiguapo.ap4medecins.entities.Role;
import io.github.nosiguapo.ap4medecins.repositories.AppUserRepository;
import io.github.nosiguapo.ap4medecins.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser aUser = appUserRepository.findByUsername(username);
        if (aUser == null){
            log.error("User not found.");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User {} found.", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // Giving user roles to Tokens
        for (Role role : aUser.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        // Returning a springsecurity user
        return new org.springframework.security.core.userdetails.User(
                aUser.getUsername(),
                aUser.getPassword(),
                authorities
        );
    }

    public AppUser saveUser(AppUser user){
        log.info("Saving new user {} to database", user.getUsername());
        // We encrypt the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return appUserRepository.save(user);
    }

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String rolename){
        log.info("Adding role {} to user {}", rolename, username);
        AppUser user = appUserRepository.findByUsername(username);
        Role role = roleRepository.findByName(rolename);

        user.getRoles().add(role);
    }

    public AppUser getUser(String username){
        return appUserRepository.findByUsername(username);
    }

    public List<AppUser> getUsers(){
        return appUserRepository.findAll();
    }
}
