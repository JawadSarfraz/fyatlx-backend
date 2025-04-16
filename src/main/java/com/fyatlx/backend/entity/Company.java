package com.fyatlx.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;
    private String country;
    private String city;
    private String size;
    private String certifications;
    private String servicesOffered;
    private String highlightProject;
    private String machinery;
}
