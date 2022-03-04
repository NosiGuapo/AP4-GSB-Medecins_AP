package io.github.nosiguapo.ap4medecins.repositories;

import io.github.nosiguapo.ap4medecins.entities.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    /* Define all SQL commands in here */

    // GET requests
    List<Medecin> findByNomLike(String lname); // Finding with name only
    List<Medecin> findByPrenomLike(String fname); // Finding with fname only
//    List<Medecin> findByNomAndPrenomOrderByNomAsc(String name); // Finding with both name and fname
    List<Medecin> findBySpecLike(String spec); // Finding with doctor's sector of activity

    // POST requests
    void deleteById(@NonNull Long id); // Delete Doctor by its Id
}
