package com.marifsulaksono.ewallet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.marifsulaksono.ewallet.entities.Balance;

public interface BalanceRepository extends CrudRepository<Balance, Long> {
    
    @Query("SELECT b FROM Balance b WHERE b.user.id = :userId")
    public List<Balance> findByUserId(@Param("userId") Long userId);
}
