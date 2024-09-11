package com.marifsulaksono.ewallet.services;

import java.util.List;
import java.util.Optional;

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

    public List<User> searchByName(String name) {
        return userRepo.searchByName("%"+name+"%");
    }

    public User getById(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (!user.isPresent()) {
            return null;
        }

        return user.get();
    }

    public User save(User user) {
        return userRepo.save(user);
    }

    public void delete(Long id) {
        userRepo.deleteById(id);
    }
}
