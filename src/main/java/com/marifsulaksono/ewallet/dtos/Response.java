package com.marifsulaksono.ewallet.dtos;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {
    private String status;
    private List<String> message = new ArrayList<>();
    private T data;
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<String> getMessage() {
        return message;
    }
    public void setMessage(List<String> message) {
        this.message = message;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
