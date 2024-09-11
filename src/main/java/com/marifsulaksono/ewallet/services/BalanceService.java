package com.marifsulaksono.ewallet.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marifsulaksono.ewallet.dtos.BalanceDto;
import com.marifsulaksono.ewallet.entities.Balance;
import com.marifsulaksono.ewallet.entities.User;
import com.marifsulaksono.ewallet.repositories.BalanceRepository;
import com.marifsulaksono.ewallet.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BalanceService {
    
    @Autowired
    private BalanceRepository balanceRepo;
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public Iterable<Balance> getAll() {
        return balanceRepo.findAll();
    }

    public List<Balance> findByUserId(Long userdId) {
        return balanceRepo.findByUserId(userdId);
    }

    public Balance save(Long id, BalanceDto data) {
        Balance balance = modelMapper.map(data, Balance.class);
        balance.setId(id);
        User user = userRepo.findById(data.getUser_id()).orElseThrow(() -> new RuntimeException("User not found"));
        balance.setUser(user);
        return balanceRepo.save(balance);
    }

    public void delete(Long id) {
        balanceRepo.deleteById(id);
    }
}
