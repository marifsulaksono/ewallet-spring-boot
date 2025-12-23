package com.marifsulaksono.ewallet.util.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserPrincipalMapper {

    private final String id;
    private final String email;
    private final String role;
}
