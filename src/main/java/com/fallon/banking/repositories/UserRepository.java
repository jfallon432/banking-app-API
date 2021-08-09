package com.fallon.banking.repositories;

import com.fallon.banking.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select case when count(au) > 0 then true else false end from User au where au.email = :email")
    boolean existsByEmail(String email);

    @Query("select case when count(au) > 0 then true else false end from User au where au.username = :username")
    boolean existByUsername(String username);

    Optional<User> getByUsernameAndPassword(String username, String password);
}
