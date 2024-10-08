package com.marifsulaksono.ewallet.controllers;

import java.util.List;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marifsulaksono.ewallet.dtos.Response;
import com.marifsulaksono.ewallet.entities.User;
import com.marifsulaksono.ewallet.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    public Iterable<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/search")
    public List<User> searchByName(@RequestParam String name) {
        return userService.searchByName(name);
    }

    @GetMapping("/filter")
    public Iterable<User> searchByName(@RequestParam String name, @RequestParam int limit, @RequestParam int page, @RequestParam String order) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("id"));
        if (order.toLowerCase().equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page - 1, limit, Sort.by("id").descending());
        }
        return userService.findByName(name, pageable);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }
    
    @PostMapping
    public ResponseEntity<Response<User>> save(@Valid @RequestBody User user, Errors errors) {
        Response<User> response = new Response<>();
        if (errors.hasErrors()) {
            for (ObjectError error: errors.getAllErrors()) {
                response.getMessage().add(error.getDefaultMessage());
            }
            response.setStatus("Failed");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.setStatus("Success");
        response.getMessage().add("Berhasil menambahkan data user");
        response.setData(userService.save(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/batch")
    public ResponseEntity<Response<Iterable<User>>> batchInsert(@Valid @RequestBody User[] users, Errors errors) {
        Response<Iterable<User>> response = new Response<>();
        if (errors.hasErrors()) {
            for (ObjectError error: errors.getAllErrors()) {
                response.getMessage().add(error.getDefaultMessage());
            }
            response.setStatus("Failed");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.setStatus("Success");
        response.getMessage().add("Berhasil menambahkan data user");
        response.setData(userService.batchInsert(Arrays.asList(users)));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<User>> updateById(@PathVariable Long id, @Valid @RequestBody User user, Errors errors) {
        user.setId(id);
        Response<User> response = new Response<>();
        if (errors.hasErrors()) {
            for (ObjectError error: errors.getAllErrors()) {
                response.getMessage().add(error.getDefaultMessage());
            }
            response.setStatus("Failed");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.setStatus("Success");
        response.getMessage().add("Berhasil mengubah data user");
        response.setData(userService.save(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }
    
}
