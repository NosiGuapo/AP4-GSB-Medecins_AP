package io.github.nosiguapo.ap4medecins.repositories;

import io.github.nosiguapo.ap4medecins.entities.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartementRepository extends JpaRepository<Departement, Long> {
    /* Define all SQL commands in here */
    List<Departement> findAllByOrderByNomAsc();
    List<Departement> findDepartementByPaysId(Long id);
    @Query("SELECT max(d.id) FROM Departement d") Long findMaxId(); // Get all archived (fully used) invitations


}
