package com.marifsulaksono.ewallet.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.marifsulaksono.ewallet.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByNameContains(String name);
}
