package io.github.nosiguapo.ap4medecins.repositories;

import com.sun.istack.NotNull;
import io.github.nosiguapo.ap4medecins.entities.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    /* Define all SQL commands in here */

    // GET requests
    List<Medecin> findByNomOrderByNomAsc(String nom); // Finding with name only
    List<Medecin> findByPrenomOrderByNomAsc(String prenom); // Finding with fname only
    List<Medecin> findByNomAndPrenom(String nom, String prenom); // Finding with both name and fname
    List<Medecin> findBySpecOrderByNomAsc(String spec); // Finding with doctor's sector of activity

    // POST requests
    void deleteById(@NonNull Long id); // Delete Doctor by its Id
}
