package com.marifsulaksono.ewallet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marifsulaksono.ewallet.entities.User;
import com.marifsulaksono.ewallet.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepo;

    public Iterable<User> getAll() {
        return userRepo.findAll();
    }

    public List<User> searchByNmae(String name) {
        return userRepo.findByNameContains(name);
    }

    public User getById(Long id) {
        return userRepo.findById(id).get();
    }

    public User save(User user) {
        user.setBalance(0);
        return userRepo.save(user);
    }

    public void delete(Long id) {
        userRepo.deleteById(id);
    }
}
