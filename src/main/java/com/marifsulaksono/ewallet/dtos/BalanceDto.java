package com.marifsulaksono.ewallet.dtos;

import jakarta.validation.constraints.NotEmpty;

public class BalanceDto {
    
    @NotEmpty(message = "Type is required")
    private String type;

    private Long user_id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    
}
