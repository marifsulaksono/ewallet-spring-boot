package com.marifsulaksono.ewallet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.marifsulaksono.ewallet.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.name LIKE :name")
    List<User> searchByName(@Param("name") String name);
    // List<User> findByNameContains(String name);
}
