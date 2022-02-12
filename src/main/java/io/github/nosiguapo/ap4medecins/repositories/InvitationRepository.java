package io.github.nosiguapo.ap4medecins.repositories;

import io.github.nosiguapo.ap4medecins.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    /* Define all SQL commands in here */
//    List<Invitation> findByOwnerIn(Integer owner_id); // Finding all invitations from an user
    List<Invitation> findByCreationIsBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate ); // Finding invites created between two specific dates
    // The query is selecting from the Object, not from the db directly
    @Query("SELECT i FROM Invitation i WHERE i.currentuses = i.maxuses") List<Invitation> findAllArchivedInvites(); // Get all archived (fully used) invitations
    @Query("SELECT i FROM Invitation i WHERE i.owner = ?1") List<Invitation> findByOwner(Long id); // Find by owner
}
