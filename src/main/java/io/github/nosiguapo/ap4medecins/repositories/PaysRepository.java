package io.github.nosiguapo.ap4medecins.repositories;

import io.github.nosiguapo.ap4medecins.entities.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaysRepository extends JpaRepository<Pays, Long> {
    /* Define all SQL commands in here */
}
