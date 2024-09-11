package com.marifsulaksono.ewallet.repositories;

import org.springframework.data.repository.CrudRepository;

import com.marifsulaksono.ewallet.entities.Balance;

public interface BalanceRepository extends CrudRepository<Balance, Long> {
    
}
