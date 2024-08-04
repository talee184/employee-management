package com.talee.employee.management.repo;

import com.talee.employee.management.model.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {


  /**
   * findByName method is used to retrieve a user by their username. It returns an Optional of
   * UserInfo, which will be empty if no user is found.
   */
  Optional<Users> findByUsername(String username);

}
