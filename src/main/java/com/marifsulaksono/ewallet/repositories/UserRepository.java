package com.marifsulaksono.ewallet.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.marifsulaksono.ewallet.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.name LIKE :name")
    List<User> searchByName(@Param("name") String name);
    // List<User> findByNameContains(String name); // Derived Query Methods. reference: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

    Page<User> findByNameContains(String name, Pageable pageable);
}
