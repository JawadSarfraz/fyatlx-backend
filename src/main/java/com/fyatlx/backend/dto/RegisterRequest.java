package com.fyatlx.backend.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String companyName;
    private String role; // EPC, Construction, Engineering
}