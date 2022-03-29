package io.github.nosiguapo.ap4medecins.repositories;

import io.github.nosiguapo.ap4medecins.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
    @Query("SELECT u FROM AppUser u WHERE u.isAdmin = true") AppUser findAdmins(); // Get all admins
    @Query("SELECT u.isAdmin FROM AppUser u WHERE u.username = ?1") AppUser getStatusOfUser(String username); // Get all admins
}
