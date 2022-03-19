package io.github.nosiguapo.ap4medecins.repositories;

import io.github.nosiguapo.ap4medecins.entities.Departement;
import io.github.nosiguapo.ap4medecins.entities.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    /* Define all SQL commands in here */

    // GET requests
    List<Medecin> findAllByOrderByNomAsc();
    List<Medecin> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String lname, String fname); //
    List<Medecin> findBySpecContainingIgnoreCase(String spec);
    List<Medecin> findBySpec(String spec);
    List<Medecin> findByDepartement(Departement departement);
    // POST requests
    void deleteById(@NonNull Long id); // Delete Doctor by its Id
}
