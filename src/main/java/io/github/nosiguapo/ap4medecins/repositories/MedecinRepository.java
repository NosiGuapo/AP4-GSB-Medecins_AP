package io.github.nosiguapo.ap4medecins.repositories;

import io.github.nosiguapo.ap4medecins.entities.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    /* Define all SQL commands in here */

    // GET requests
    List<Medecin> findAllByOrderByNomAsc();
    List<Medecin> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String lname, String fname); //
    @Query("SELECT distinct m.spec FROM Medecin m WHERE m.spec IS NOT NULL") List<String> findAllSpecs(); // Ge
    @Query("SELECT max(m.id) FROM Medecin m") Long findMaxId(); // Get all archived (fully used) invitations
    List<Medecin> findBySpecIgnoreCase(String spec);
    List<Medecin> findMedecinByDepartementId(Long id);

    // POST requests
    void deleteById(@NonNull Long id); // Delete Doctor by its Id
}
