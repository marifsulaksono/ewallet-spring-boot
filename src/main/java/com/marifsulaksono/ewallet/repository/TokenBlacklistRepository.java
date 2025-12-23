package com.marifsulaksono.ewallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marifsulaksono.ewallet.entity.TokenBlacklist;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {

    boolean existsByToken(String token);
}
