package com.marifsulaksono.ewallet.dto.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Data;

@Data
public abstract class BasePageRequest {
    private int page = 0;
    private int size = 10;
    private String sortBy = "id";
    private String sortDir = "asc";
    private String search; // keyword search (optional)

    public Pageable toPageable() {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        return PageRequest.of(page, size, sort);
    }
}
