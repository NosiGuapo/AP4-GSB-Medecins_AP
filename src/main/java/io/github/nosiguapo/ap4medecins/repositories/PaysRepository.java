package io.github.nosiguapo.ap4medecins.repositories;

import io.github.nosiguapo.ap4medecins.entities.Invitation;
import io.github.nosiguapo.ap4medecins.entities.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PaysRepository extends JpaRepository<Pays, Long> {
    // POST
    void deleteById(@NonNull Long id);
    List<Pays> findByNomIgnoreCase(String name);
    @Query("SELECT max(p.id) FROM Pays p") Long findMaxId(); // Get all archived (fully used) invitations

}
