package io.github.nosiguapo.ap4medecins.repositories;

import io.github.nosiguapo.ap4medecins.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
