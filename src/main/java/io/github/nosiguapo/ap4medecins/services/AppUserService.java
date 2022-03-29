package io.github.nosiguapo.ap4medecins.services;

import io.github.nosiguapo.ap4medecins.entities.AppUser;
import io.github.nosiguapo.ap4medecins.entities.Role;
import io.github.nosiguapo.ap4medecins.repositories.AppUserRepository;
import io.github.nosiguapo.ap4medecins.repositories.RoleRepository;
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
    private final RoleRepository roleRepository;

    public AppUser saveUser(AppUser user){
        log.info("Saving new user {} to database", user.getUsername());
        return appUserRepository.save(user);
    }

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String rolename){
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

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    public Role getRole(String rolename){
        return roleRepository.findByName(rolename);
    }
}
