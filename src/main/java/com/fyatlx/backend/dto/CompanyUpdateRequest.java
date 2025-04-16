package com.fyatlx.backend.dto;

import lombok.Data;

@Data
public class CompanyUpdateRequest {
    private String description;
    private String country;
    private String city;
    private String size;
    private String certifications;
    private String servicesOffered;
    private String highlightProject;
    private String machinery;
}
