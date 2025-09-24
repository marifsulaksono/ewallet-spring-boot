package com.marifsulaksono.ewallet.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageRequest extends BasePageRequest {
    private String role; // filter khusus User
}
