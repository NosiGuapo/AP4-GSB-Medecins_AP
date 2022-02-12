package io.github.nosiguapo.ap4medecins.repositories;

import io.github.nosiguapo.ap4medecins.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    /* Define all SQL commands in here */
    List<User> findByFname(String fname); // Finding with first name only
    List<User> findByLname(String lname); // Finding with last name only
    List<User> findByLnameAndFname(String lname, String fname); // Finding with both first and last names
    List<User> findByUsername(String username); // Finding with username only
    List<User> findByFnameAndLnameAndUsername(String fname, String lname, String username); // Finding with all possible name types
    List<User> findByRegisterIsBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate ); // Finding users registered between two specific dates
}
