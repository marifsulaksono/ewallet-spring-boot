package com.marifsulaksono.ewallet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marifsulaksono.ewallet.dtos.BalanceDto;
import com.marifsulaksono.ewallet.dtos.Response;
import com.marifsulaksono.ewallet.entities.Balance;
import com.marifsulaksono.ewallet.services.BalanceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/balances")
public class BalanceController {
    
    @Autowired
    private BalanceService balanceService;

    @GetMapping
    public Iterable<Balance> getAll() {
        return balanceService.getAll();
    }
    
    @PostMapping
    public ResponseEntity<Response<Balance>> save(@Valid @RequestBody BalanceDto balance, Errors errors) {
        Response<Balance> response = new Response<>();
        if (errors.hasErrors()) {
            for (ObjectError error: errors.getAllErrors()) {
                response.getMessage().add(error.getDefaultMessage());
            }
            response.setStatus("Failed");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.setStatus("Success");
        response.getMessage().add("Berhasil menambahkan data balance");
        response.setData(balanceService.save(null, balance));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Balance>> updateById(@PathVariable Long id, @Valid @RequestBody BalanceDto balance, Errors errors) {
        Response<Balance> response = new Response<>();
        if (errors.hasErrors()) {
            for (ObjectError error: errors.getAllErrors()) {
                response.getMessage().add(error.getDefaultMessage());
            }
            response.setStatus("Failed");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.setStatus("Success");
        response.getMessage().add("Berhasil mengubah data balance");
        response.setData(balanceService.save(id, balance));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        balanceService.delete(id);
    }
}
