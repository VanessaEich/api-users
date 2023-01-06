package net.vanessa.users.repositories;

import net.vanessa.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * This repository handles transactions between the database and the User entity
 * @author Vanessa Eich on 02/01/2023
 */

public interface IUsersRepository extends JpaRepository<User, Long> {

    /**
     * This method lists all users records according to the name
     * @return the list of all users record
     * @author Vanessa Eich on 02/01/2023
     */
    @Query(nativeQuery = true, value= "select user_id, name, birth_date from users where name like (:name)")
    List<User> findByName(@Param("name") String name);
}


