package io.github.nosiguapo.ap4medecins.repositories;

import io.github.nosiguapo.ap4medecins.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    /* Define all SQL commands in here */

    // GET
    List<User> findByFnameLike(String fname); // Finding with first name only
    List<User> findByLnameLike(String lname); // Finding with last name only
//    List<User> findByLnameAndFname(String name); // Finding with both first and last names
    List<User> findByUsernameLike(String username); // Finding with username only
//    List<User> findByFnameAndLnameAndUsername(String name); // Finding with all possible name types
    List<User> findByRegisterIsBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate ); // Finding users registered between two specific dates

    // POST
}
