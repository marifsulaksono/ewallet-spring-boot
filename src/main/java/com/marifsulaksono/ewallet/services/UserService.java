package com.marifsulaksono.ewallet.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.marifsulaksono.ewallet.entities.User;
import com.marifsulaksono.ewallet.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Iterable<User> getAll() {
        return userRepo.findAll();
    }

    public List<User> searchByName(String name) {
        return userRepo.searchByName("%"+name+"%");
    }

    public Iterable<User> findByName(String name, Pageable pageable) {
        return userRepo.findByNameContains(name, pageable);
    }

    public User getById(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (!user.isPresent()) {
            return null;
        }

        return user.get();
    }

    public User save(User user) {
        boolean existUser = userRepo.findByEmail(user.getEmail()).isPresent();
        if (existUser) {
            throw new RuntimeException(String.format("User with email '%s' already exists", user.getEmail()));
        }

        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepo.save(user);
    }

    public Iterable<User> batchInsert(Iterable<User> users) {
        return userRepo.saveAll(users);
    }

    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email)
            .orElseThrow(() -> 
                new UsernameNotFoundException(String.format("User with email '%s' not found", email)));
    }
}
