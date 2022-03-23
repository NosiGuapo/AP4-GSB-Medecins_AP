package io.github.nosiguapo.ap4medecins.repositories;

import io.github.nosiguapo.ap4medecins.entities.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartementRepository extends JpaRepository<Departement, Long> {
    /* Define all SQL commands in here */
    List<Departement> findAllByOrderByNomAsc();
    List<Departement> findDepartementByPaysId(Long id);

}
